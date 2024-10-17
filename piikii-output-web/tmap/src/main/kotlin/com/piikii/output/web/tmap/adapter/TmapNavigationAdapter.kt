package com.piikii.output.web.tmap.adapter

import com.piikii.application.domain.course.Coordinate
import com.piikii.application.domain.course.Distance
import com.piikii.application.domain.place.Place
import com.piikii.application.port.output.web.NavigationPort
import com.piikii.common.logutil.SlackHookLogger
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.util.concurrent.TimeUnit

@Component
class TmapNavigationAdapter(
    private val tmapApiClient: RestClient,
    private val redissonClient: RedissonClient,
    private val hookLogger: SlackHookLogger,
) : NavigationPort {
    @Cacheable(
        value = ["Distance"],
        key = "#startPlace.id + '_' + #endPlace.id",
        unless = "#result == T(com.piikii.application.domain.course.Distance).EMPTY",
    )
    @Retryable(
        value = [InterruptedException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000)
    )
    override fun getDistance(
        startPlace: Place,
        endPlace: Place,
    ): Distance {
        val startCoordinate = startPlace.getCoordinate()
        val endCoordinate = endPlace.getCoordinate()

        if (!startCoordinate.isValid() || !endCoordinate.isValid()) {
            return Distance.EMPTY
        }

        val lockKey = "distance-lock-${startPlace.id}_${endPlace.id}"
        val lock: RLock = redissonClient.getLock(lockKey)

        return try {
            // 락 획득 시도: 10초 대기, 5초 동안 락을 유지
            if (lock.tryLock(10, 5, TimeUnit.SECONDS)) {
                getDistanceFromTmap(startCoordinate, endCoordinate)
            } else {
                throw InterruptedException("락 획득 실패")
            }
        } finally {
            if (lock.isHeldByCurrentThread) {
                lock.unlock()
            }
        }
    }

    @Recover
    fun recoverFromInterruptedException(
        exception: InterruptedException,
        startPlace: Place,
        endPlace: Place
    ): Distance {
        hookLogger.send(exception.message?:"[redis] 알 수 없는 에러")
        val startCoordinate = startPlace.getCoordinate()
        val endCoordinate = endPlace.getCoordinate()
        return if (startCoordinate.isValid() && endCoordinate.isValid()) {
            // 락 획득 실패 시 바로 외부 API 호출
            getDistanceFromTmap(startCoordinate, endCoordinate)
        } else {
            Distance.EMPTY
        }
    }

    private fun getDistanceFromTmap(
        start: Coordinate,
        end: Coordinate,
    ): Distance {
        return tmapApiClient.post()
            .uri("")
            .body(
                TmapRouteInfoRequest(
                    startX = start.x!!,
                    startY = start.y!!,
                    endX = end.x!!,
                    endY = end.y!!,
                ),
            )
            .retrieve()
            .body<TmapRouteInfoResponse>()
            ?.toDistance()
            ?: getEmptyDistanceWithError(start, end)
    }

    private fun getEmptyDistanceWithError(
        start: Coordinate,
        end: Coordinate,
    ): Distance {
        val message = "fail to request tmap api call, start(${start.x}, ${start.y}), end(${end.x}, ${end.y})"
        hookLogger.send(message)
        return Distance.EMPTY
    }
}

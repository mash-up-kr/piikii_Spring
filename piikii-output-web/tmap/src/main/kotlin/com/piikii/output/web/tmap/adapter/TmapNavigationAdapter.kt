package com.piikii.output.web.tmap.adapter

import com.piikii.application.domain.course.Coordinate
import com.piikii.application.domain.course.Distance
import com.piikii.application.domain.place.Place
import com.piikii.application.port.output.web.NavigationPort
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.util.concurrent.TimeUnit

@Component
class TmapNavigationAdapter(
    private val tmapApiClient: RestClient,
    private val redissonClient: RedissonClient,
) : NavigationPort {
    private val logger: Logger = LoggerFactory.getLogger(TmapNavigationAdapter::class.java)

    @Cacheable(
        value = ["Distance"],
        key = "#startPlace.id + '_' + #endPlace.id",
        unless = "#result == T(com.piikii.application.domain.course.Distance).EMPTY",
    )
    override fun getDistance(
        startPlace: Place,
        endPlace: Place,
    ): Distance {
        val startCoordinate = startPlace.getCoordinate()
        val endCoordinate = endPlace.getCoordinate()
        return if (startCoordinate.isValid() && endCoordinate.isValid()) {
            val lockKey = "distance-lock-${startPlace.id}_${endPlace.id}"
            val lock: RLock = redissonClient.getLock(lockKey)

            try {
                // 락 획득 시도, 10초 동안 기다리고 5초 동안 락을 유지
                if (lock.tryLock(10, 5, TimeUnit.SECONDS)) {
                    getDistanceFromTmap(startCoordinate, endCoordinate)
                } else {
                    // 락을 획득하지 못한 경우 대기&재시도 또는 기본 값 반환
                    Distance.EMPTY
                }
            } finally {
                if (lock.isHeldByCurrentThread) {
                    lock.unlock()
                }
            }
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
            ?: getEmptyDistanceOfError(start, end)
    }

    private fun getEmptyDistanceOfError(
        start: Coordinate,
        end: Coordinate,
    ): Distance {
        logger.error("fail to request tmap api call, start(${start.x}, ${start.y}), end(${end.x}, ${end.y})")
        return Distance.EMPTY
    }
}

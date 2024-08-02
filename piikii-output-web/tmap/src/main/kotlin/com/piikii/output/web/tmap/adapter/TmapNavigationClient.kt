package com.piikii.output.web.tmap.adapter

import com.piikii.application.domain.course.Coordinate
import com.piikii.application.domain.course.Distance
import com.piikii.application.port.output.web.NavigationClient
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class TmapNavigationClient(
    private val tmapApiClient: RestClient,
) : NavigationClient {
    override fun getDistance(
        start: Coordinate,
        end: Coordinate,
    ): Distance {
        return if (start.isValid() && end.isValid()) {
            getDistanceFromTmap(start, end)
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
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.ROUTE_PROCESS_ERROR,
                detailMessage = "fail to request tmap api call, start(${start.x}, ${start.y}), end(${end.x}, ${end.y})",
            )
    }
}

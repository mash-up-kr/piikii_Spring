package com.piikii.output.web.tmap.adapter

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
    override fun getDistanceBetweenPlaces(
        startX: Double?,
        startY: Double?,
        endX: Double?,
        endY: Double?,
    ): Distance {
        return if (validateLocations(startX, startY, endX, endY)) {
            try {
                return tmapApiClient.post()
                    .uri("")
                    .body(
                        TmapRouteInfoRequest(
                            startX = startX!!,
                            startY = startY!!,
                            endX = endX!!,
                            endY = endY!!,
                            startName = "default",
                            endName = "default",
                        ),
                    )
                    .retrieve()
                    .body<TmapRouteInfoResponse>()
                    ?.toDistance()
                    ?: throw PiikiiException(
                        exceptionCode = ExceptionCode.ROUTE_PROCESS_ERROR,
                        detailMessage = "fail to request tmap api call, start: ($startX, $startY), end: ($endX, $endY)",
                    )
            } catch (exception: RuntimeException) {
                throw PiikiiException(
                    exceptionCode = ExceptionCode.ROUTE_PROCESS_ERROR,
                    detailMessage = exception.message ?: "fail to request tmap api call",
                )
            }
        } else {
            Distance.empty()
        }
    }

    private fun validateLocations(
        startX: Double?,
        startY: Double?,
        endX: Double?,
        endY: Double?,
    ): Boolean {
        return !(startX == null || startY == null || endX == null || endY == null)
    }
}

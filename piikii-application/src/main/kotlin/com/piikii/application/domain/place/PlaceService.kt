package com.piikii.application.domain.place

import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.port.input.PlaceUseCase
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse.Companion.groupingByPlaceType
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class PlaceService(
    private val placeQueryPort: PlaceQueryPort,
    private val placeCommandPort: PlaceCommandPort,
    private val scheduleQueryPort: ScheduleQueryPort,
) : PlaceUseCase {
    @Transactional
    override fun addPlace(
        targetRoomId: UUID,
        addPlaceRequest: AddPlaceRequest,
    ): PlaceResponse {
        return PlaceResponse(
            placeCommandPort.save(
                targetRoomId = targetRoomId,
                scheduleId = addPlaceRequest.scheduleId,
                place = addPlaceRequest.toDomain(targetRoomId),
            ),
        )
    }

    override fun retrieveAllByRoomId(roomId: UUID): List<PlaceTypeGroupResponse> {
        val placeScheduleMap = mutableMapOf<Place, Schedule>()
        val places = placeQueryPort.retrieveAllByRoomId(roomId)
        for (place in places) {
            placeScheduleMap[place] = scheduleQueryPort.findScheduleById(place.scheduleId)
        }
        return groupingByPlaceType(placeScheduleMap)
    }

    @Transactional
    override fun modify(
        roomId: UUID,
        targetPlaceId: Long,
        modifyPlaceRequest: ModifyPlaceRequest,
    ): PlaceResponse {
        return PlaceResponse(
            placeCommandPort.update(
                targetPlaceId = targetPlaceId,
                place = modifyPlaceRequest.toDomain(targetPlaceId, roomId),
            ),
        )
    }

    @Transactional
    override fun delete(targetPlaceId: Long) {
        placeCommandPort.delete(targetPlaceId)
    }
}

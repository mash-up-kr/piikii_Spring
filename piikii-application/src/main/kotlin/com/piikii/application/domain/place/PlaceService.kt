package com.piikii.application.domain.place

import com.piikii.application.port.input.PlaceUseCase
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse.Companion.groupingByPlaceType
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class PlaceService(
    private val placeQueryPort: PlaceQueryPort,
    private val placeCommandPort: PlaceCommandPort,
) : PlaceUseCase {
    @Transactional
    override fun addPlace(
        roomId: UUID,
        addPlaceRequest: AddPlaceRequest,
    ): PlaceResponse {
        return PlaceResponse(
            placeCommandPort.save(
                targetRoomId = roomId,
                place = addPlaceRequest.toDomain(roomId),
            ),
        )
    }

    override fun findAllByRoomId(roomId: UUID): List<PlaceTypeGroupResponse> {
        return groupingByPlaceType(placeQueryPort.retrieveAllByRoomId(roomId))
    }

    @Transactional
    override fun modify(
        roomId: UUID,
        targetPlaceId: Long,
        modifyPlaceRequest: ModifyPlaceRequest,
    ) {
        placeCommandPort.update(
            targetPlaceId = targetPlaceId,
            place = modifyPlaceRequest.toDomain(roomId, targetPlaceId),
        )
    }

    @Transactional
    override fun delete(targetPlaceId: Long) {
        placeCommandPort.delete(targetPlaceId)
    }
}

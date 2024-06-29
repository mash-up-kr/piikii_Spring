package com.piikii.application.domain.roomplace

import com.piikii.application.port.input.roomplace.RoomPlaceUseCase
import com.piikii.application.port.input.roomplace.dto.AddRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.ModifyRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.RoomPlaceCategoryGroupResponse
import com.piikii.application.port.input.roomplace.dto.RoomPlaceCategoryGroupResponse.Companion.groupingByCategoryName
import com.piikii.application.port.input.roomplace.dto.RoomPlaceResponse
import com.piikii.application.port.output.persistence.RoomPlaceCommandPort
import com.piikii.application.port.output.persistence.RoomPlaceQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class RoomPlaceService(
    private val roomPlaceQueryPort: RoomPlaceQueryPort,
    private val roomPlaceCommandPort: RoomPlaceCommandPort,
) : RoomPlaceUseCase {
    @Transactional
    override fun addRoomPlace(
        targetRoomId: UUID,
        addRoomPlaceRequest: AddRoomPlaceRequest,
    ): RoomPlaceResponse {
        return RoomPlaceResponse(
            roomPlaceCommandPort.save(
                targetRoomId = targetRoomId,
                roomPlace = addRoomPlaceRequest.toDomain(),
            ),
        )
    }

    override fun retrieveAllByRoomId(roomId: UUID): List<RoomPlaceCategoryGroupResponse> {
        return groupingByCategoryName(roomPlaceQueryPort.retrieveAllByRoomId(roomId))
    }

    @Transactional
    override fun modify(
        targetRoomId: Long,
        modifyRoomPlaceRequest: ModifyRoomPlaceRequest,
    ) {
        roomPlaceCommandPort.update(
            targetRoomPlaceId = targetRoomId,
            roomPlace = modifyRoomPlaceRequest.toDomain(targetRoomId),
        )
    }

    @Transactional
    override fun delete(targetRoomPlaceId: Long) {
        roomPlaceCommandPort.delete(targetRoomPlaceId)
    }
}

package com.piikii.application.domain.place

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.image.ImageFolderType
import com.piikii.application.port.input.PlaceUseCase
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.ScheduleTypeGroupResponse
import com.piikii.application.port.output.objectstorage.ObjectStoragePort
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional(readOnly = true)
class PlaceService(
    private val roomQueryPort: RoomQueryPort,
    private val scheduleQueryPort: ScheduleQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val placeCommandPort: PlaceCommandPort,
    private val objectStoragePort: ObjectStoragePort,
) : PlaceUseCase {
    @Transactional
    override fun addPlace(
        targetRoomUid: UuidTypeId,
        addPlaceRequest: AddPlaceRequest,
        placeImageUrls: List<String>,
    ): List<PlaceResponse> {
        val room = roomQueryPort.findById(targetRoomUid)
        val schedules = scheduleQueryPort.findAllByIds(addPlaceRequest.scheduleIds.map(::LongTypeId))
        val places =
            schedules.map { schedule ->
                addPlaceRequest.toDomain(
                    roomUid = room.roomUid,
                    scheduleId = schedule.id,
                    imageUrls = placeImageUrls,
                )
            }

        return placeCommandPort.saveAll(
            roomUid = room.roomUid,
            places = places,
        ).map(::PlaceResponse)
    }

    override fun findAllByRoomUidGroupByPlaceType(roomUid: UuidTypeId): List<ScheduleTypeGroupResponse> {
        val scheduleById = scheduleQueryPort.findAllByRoomUid(roomUid).associateBy { it.id }
        return placeQueryPort.findAllByRoomUid(roomUid).groupBy { it.scheduleId }
            .mapNotNull { (scheduleId, places) ->
                scheduleById[scheduleId]?.let { schedule ->
                    ScheduleTypeGroupResponse(
                        scheduleId = scheduleId.getValue(),
                        scheduleName = schedule.name,
                        type = schedule.type,
                        places = places.map { place -> PlaceResponse(place = place) },
                    )
                }
            }
    }

    @Transactional
    override fun modify(
        targetRoomUid: UuidTypeId,
        targetPlaceId: LongTypeId,
        modifyPlaceRequest: ModifyPlaceRequest,
        newPlaceImages: List<MultipartFile>?,
    ): PlaceResponse {
        val updatedUrls =
            newPlaceImages?.let {
                objectStoragePort.updateAllByUrls(
                    imageFolderType = BUCKET_TYPE,
                    deleteTargetUrls = modifyPlaceRequest.deleteTargetUrls,
                    newMultipartFiles = it,
                ).get()
            } ?: listOf()

        val place = isPlaceNullOrGet(targetPlaceId)
        return PlaceResponse(
            placeCommandPort.update(
                targetPlaceId = targetPlaceId,
                place =
                    modifyPlaceRequest.toDomain(
                        targetPlaceId,
                        targetRoomUid,
                        place.origin,
                        LongTypeId(modifyPlaceRequest.scheduleId),
                        filterDuplicateUrls(updatedUrls, modifyPlaceRequest.deleteTargetUrls, place),
                    ),
            ),
        )
    }

    @Transactional
    override fun delete(targetPlaceId: LongTypeId) {
        objectStoragePort.deleteAllByUrls(
            imageFolderType = BUCKET_TYPE,
            deleteTargetUrls = isPlaceNullOrGet(targetPlaceId).thumbnailLinks.convertToList,
        )
        placeCommandPort.delete(targetPlaceId)
    }

    private fun isPlaceNullOrGet(targetPlaceId: LongTypeId): Place {
        return placeQueryPort.findByPlaceId(targetPlaceId)
    }

    private fun filterDuplicateUrls(
        updatedUrls: List<String>,
        deletedUrls: List<String>,
        place: Place,
    ): List<String> {
        return (updatedUrls + place.thumbnailLinks.convertToList)
            .toSet()
            .filterNot { deletedUrls.contains(it) }
            .toList()
    }

    companion object {
        private val BUCKET_TYPE = ImageFolderType.PLACE
    }
}

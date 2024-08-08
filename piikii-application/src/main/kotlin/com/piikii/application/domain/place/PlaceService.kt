package com.piikii.application.domain.place

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.PlaceUseCase
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.ScheduleTypeGroupResponse
import com.piikii.application.port.output.objectstorage.BucketFolderType
import com.piikii.application.port.output.objectstorage.ObjectStoragePort
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
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
        placeImages: List<MultipartFile>?,
    ): PlaceResponse {
        val imageUrls =
            placeImages?.let {
                objectStoragePort.uploadAll(
                    bucketFolderType = BUCKET_TYPE,
                    multipartFiles = it,
                ).get()
            } ?: listOf()

        val room = roomQueryPort.findById(targetRoomUid)
        val schedule = scheduleQueryPort.findScheduleById(LongTypeId(addPlaceRequest.scheduleId))
        val place =
            addPlaceRequest.toDomain(
                roomUid = room.roomUid,
                // TODO 이거 좀 예쁘게 처리하는법 알아보기
                scheduleId = schedule.id,
                imageUrls = imageUrls,
            )

        return PlaceResponse(
            placeCommandPort.save(
                roomUid = room.roomUid,
                scheduleId = schedule.id,
                place = place,
            ),
        )
    }

    override fun findAllByRoomUidGroupByPlaceType(roomUid: UuidTypeId): List<ScheduleTypeGroupResponse> {
        val scheduleById = scheduleQueryPort.findAllByRoomUid(roomUid).associateBy { it.id }
        return placeQueryPort.findAllByRoomUid(roomUid).groupBy { it.scheduleId }
            .map { (scheduleId, places) ->
                val schedule = scheduleById[scheduleId] ?: throw PiikiiException(ExceptionCode.NOT_FOUNDED)
                ScheduleTypeGroupResponse(
                    scheduleId = scheduleId.getValue(),
                    scheduleName = schedule.name,
                    places = places.map { place -> PlaceResponse(place = place) },
                )
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
                    bucketFolderType = BUCKET_TYPE,
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
                        LongTypeId(modifyPlaceRequest.scheduleId),
                        filterDuplicateUrls(updatedUrls, place),
                    ),
            ),
        )
    }

    @Transactional
    override fun delete(targetPlaceId: LongTypeId) {
        objectStoragePort.deleteAllByUrls(
            bucketFolderType = BUCKET_TYPE,
            deleteTargetUrls = isPlaceNullOrGet(targetPlaceId).thumbnailLinks.convertToList,
        )
        placeCommandPort.delete(targetPlaceId)
    }

    private fun isPlaceNullOrGet(targetPlaceId: LongTypeId): Place {
        return placeQueryPort.findByPlaceId(targetPlaceId) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "targetPlaceId : $targetPlaceId",
        )
    }

    private fun filterDuplicateUrls(
        updatedUrls: List<String>,
        place: Place,
    ): List<String> {
        val set = mutableSetOf<String>()
        set.addAll(updatedUrls)
        set.addAll(place.thumbnailLinks.convertToList)
        return set.toList()
    }

    companion object {
        private val BUCKET_TYPE = BucketFolderType.PLACE
    }
}

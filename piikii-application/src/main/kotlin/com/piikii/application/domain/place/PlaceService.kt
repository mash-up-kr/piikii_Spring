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
import com.piikii.application.port.output.persistence.SchedulePlaceCommandPort
import com.piikii.application.port.output.persistence.SchedulePlaceQueryPort
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
    private val schedulePlaceQueryPort: SchedulePlaceQueryPort,
    private val schedulePlaceCommandPort: SchedulePlaceCommandPort,
    private val objectStoragePort: ObjectStoragePort,
) : PlaceUseCase {
    @Transactional
    override fun addPlace(
        targetRoomUid: UuidTypeId,
        addPlaceRequest: AddPlaceRequest,
        placeImages: List<MultipartFile>?,
    ): List<PlaceResponse> {
        val imageUrls =
            placeImages?.let {
                objectStoragePort.uploadAll(
                    bucketFolderType = BUCKET_TYPE,
                    multipartFiles = it,
                ).get()
            } ?: listOf()

        val room = roomQueryPort.findById(targetRoomUid)
        val place = addPlaceRequest.toDomain(room.roomUid, imageUrls)
        val scheduleIds = scheduleQueryPort.findAllByIds(addPlaceRequest.scheduleIds.map(::LongTypeId)).map { it.id }

        return placeCommandPort.save(room.roomUid, scheduleIds, place).map { PlaceResponse(it, place) }
    }

    override fun findAllByRoomUidGroupByPlaceType(roomUid: UuidTypeId): List<ScheduleTypeGroupResponse> {
        val schedulePlaces = schedulePlaceQueryPort.findAllByRoomUid(roomUid)
        val scheduleById = scheduleQueryPort.findAllByRoomUid(roomUid).associateBy { it.id }
        val placeById = placeQueryPort.findAllByRoomUid(roomUid).associateBy { it.id }

        return schedulePlaces.groupBy { it.scheduleId }
            .map { (scheduleId, schedulePlaces) ->
                val schedule = scheduleById[scheduleId] ?: throw PiikiiException(ExceptionCode.NOT_FOUNDED)
                ScheduleTypeGroupResponse(
                    scheduleId = scheduleId.getValue(),
                    scheduleName = schedule.name,
                    places =
                        schedulePlaces.map { schedulePlace ->
                            val place =
                                placeById[schedulePlace.placeId]
                                    ?: throw PiikiiException(ExceptionCode.NOT_FOUNDED)
                            PlaceResponse(schedulePlace, place)
                        },
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

        val schedulePlace = schedulePlaceQueryPort.findById(targetPlaceId)
        val place = isPlaceNullOrGet(schedulePlace.placeId)

        val updatedPlace =
            placeCommandPort.update(
                targetPlaceId = schedulePlace.placeId,
                place =
                    modifyPlaceRequest.toDomain(
                        targetPlaceId = schedulePlace.placeId,
                        roomUid = targetRoomUid,
                        updatedUrls = filterDuplicateUrls(updatedUrls, place),
                    ),
            )

        return PlaceResponse(schedulePlace, updatedPlace)
    }

    @Transactional
    override fun delete(targetPlaceId: LongTypeId) {
        val schedulePlace = schedulePlaceQueryPort.findById(targetPlaceId)
        val place = isPlaceNullOrGet(schedulePlace.placeId)

        objectStoragePort.deleteAllByUrls(
            bucketFolderType = BUCKET_TYPE,
            deleteTargetUrls = place.thumbnailLinks.convertToList,
        )
        schedulePlaceCommandPort.deleteAllByPlaceId(place.id)
        placeCommandPort.delete(place.id)
    }

    private fun isPlaceNullOrGet(targetPlaceId: LongTypeId): Place {
        return placeQueryPort.findByPlaceId(targetPlaceId)
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

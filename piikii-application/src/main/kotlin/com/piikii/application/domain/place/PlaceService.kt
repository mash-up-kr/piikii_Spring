package com.piikii.application.domain.place

import com.piikii.application.domain.schedule.PlaceType
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.port.input.PlaceUseCase
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import com.piikii.application.port.output.objectstorage.BucketFolderType
import com.piikii.application.port.output.objectstorage.ObjectStoragePort
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
@Transactional(readOnly = true)
class PlaceService(
    private val roomQueryPort: RoomQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val placeCommandPort: PlaceCommandPort,
    private val objectStoragePort: ObjectStoragePort,
) : PlaceUseCase {
    @Transactional
    override fun addPlace(
        targetRoomId: UUID,
        addPlaceRequest: AddPlaceRequest,
        placeImages: List<MultipartFile>?,
    ): PlaceResponse {
        checkRoomExistsOrThrow(targetRoomId)

        val imageUrls =
            placeImages?.let {
                objectStoragePort.uploadAll(
                    bucketFolderType = BUCKET_TYPE,
                    multipartFiles = it,
                ).get()
            } ?: listOf()

        return PlaceResponse(
            placeCommandPort.save(
                targetRoomId = targetRoomId,
                scheduleId = addPlaceRequest.scheduleId,
                place = addPlaceRequest.toDomain(targetRoomId, imageUrls),
            ),
        )
    }

    override fun findAllByRoomId(targetRoomId: UUID): List<PlaceTypeGroupResponse> {
        val placeTypeGroups = mutableMapOf<PlaceType, MutableList<PlaceSchedulePair>>()

        val placesWithSchedules = placeQueryPort.findAllWithSchedulesByRoomId(targetRoomId)

        for ((place, schedule) in placesWithSchedules) {
            val placeType = schedule.placeType
            val pair = PlaceSchedulePair(place, schedule)
            placeTypeGroups.getOrPut(placeType) { mutableListOf() }.add(pair)
        }

        return placeTypeGroups.map { (placeType, pairs) ->
            PlaceTypeGroupResponse(
                placeType = placeType,
                places =
                    pairs.map {
                        PlaceResponse(
                            it.place,
                        )
                    },
            )
        }
    }

    data class PlaceSchedulePair(val place: Place, val schedule: Schedule)

    @Transactional
    override fun modify(
        targetRoomId: UUID,
        targetPlaceId: Long,
        modifyPlaceRequest: ModifyPlaceRequest,
        newPlaceImages: List<MultipartFile>?,
    ): PlaceResponse {
        checkRoomExistsOrThrow(targetRoomId)

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
                        targetRoomId,
                        combineOriginalAndNewUrls(updatedUrls, place),
                    ),
            ),
        )
    }

    @Transactional
    override fun delete(targetPlaceId: Long) {
        objectStoragePort.deleteAllByUrls(
            bucketFolderType = BUCKET_TYPE,
            deleteTargetUrls = isPlaceNullOrGet(targetPlaceId).thumbnailLinks.convertToList,
        )
        placeCommandPort.delete(targetPlaceId)
    }

    private fun checkRoomExistsOrThrow(targetRoomId: UUID) {
        if (roomQueryPort.findById(targetRoomId) == null) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "targetRoomId : $targetRoomId",
            )
        }
    }

    private fun isPlaceNullOrGet(targetPlaceId: Long): Place {
        return placeQueryPort.findByPlaceId(targetPlaceId) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "targetPlaceId : $targetPlaceId",
        )
    }

    private fun combineOriginalAndNewUrls(
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

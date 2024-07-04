package com.piikii.application.domain.place

import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.port.input.PlaceUseCase
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse.Companion.groupingByPlaceType
import com.piikii.application.port.output.objectstorage.BucketFolderType
import com.piikii.application.port.output.objectstorage.ObjectStoragePort
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
@Transactional(readOnly = true)
class PlaceService(
    private val placeQueryPort: PlaceQueryPort,
    private val placeCommandPort: PlaceCommandPort,
    private val scheduleQueryPort: ScheduleQueryPort,
    private val objectStoragePort: ObjectStoragePort,
) : PlaceUseCase {
    @Transactional
    override fun addPlace(
        targetRoomId: UUID,
        addPlaceRequest: AddPlaceRequest,
        multipartFiles: List<MultipartFile>?,
    ): PlaceResponse {
        var imageUrls = listOf<String>()
        if (multipartFiles != null) {
            imageUrls =
                objectStoragePort.uploadAll(
                    bucketFolderType = BUCKET_TYPE,
                    multipartFiles = multipartFiles,
                )
        }

        return PlaceResponse(
            placeCommandPort.save(
                targetRoomId = targetRoomId,
                scheduleId = addPlaceRequest.scheduleId,
                place = addPlaceRequest.toDomain(targetRoomId, imageUrls),
            ),
        )
    }

    override fun findAllByRoomId(roomId: UUID): List<PlaceTypeGroupResponse> {
        val placeScheduleMap = mutableMapOf<Place, Schedule>()
        val places = placeQueryPort.findAllByRoomId(roomId)
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
        newMultipartFiles: List<MultipartFile>?,
    ): PlaceResponse {
        var updatedUrls = listOf<String>()
        if (newMultipartFiles != null) {
            updatedUrls =
                objectStoragePort.updateAllByUrls(
                    bucketFolderType = BUCKET_TYPE,
                    deleteTargetUrls = modifyPlaceRequest.deleteTargetUrls,
                    newMultipartFiles = newMultipartFiles,
                )
        }

        val place = isPlaceNullOrGet(targetPlaceId)
        return PlaceResponse(
            placeCommandPort.update(
                targetPlaceId = targetPlaceId,
                place =
                    modifyPlaceRequest.toDomain(
                        targetPlaceId,
                        roomId,
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

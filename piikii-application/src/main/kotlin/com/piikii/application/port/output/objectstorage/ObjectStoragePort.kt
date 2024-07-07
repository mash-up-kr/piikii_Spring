package com.piikii.application.port.output.objectstorage

import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.Future

interface ObjectStoragePort {
    fun uploadAll(
        bucketFolderType: BucketFolderType,
        multipartFiles: List<MultipartFile>,
    ): Future<List<String>>

    fun updateAllByUrls(
        bucketFolderType: BucketFolderType,
        deleteTargetUrls: List<String>,
        newMultipartFiles: List<MultipartFile>,
    ): Future<List<String>>

    fun deleteAllByUrls(
        bucketFolderType: BucketFolderType,
        deleteTargetUrls: List<String>,
    )
}

package com.piikii.application.port.output.objectstorage

import org.springframework.web.multipart.MultipartFile

interface ObjectStoragePort {
    fun uploadAll(
        bucketFolderType: BucketFolderType,
        multipartFiles: List<MultipartFile>,
    ): List<String>

    fun updateAllByUrls(
        bucketFolderType: BucketFolderType,
        deleteTargetUrls: List<String>,
        newMultipartFiles: List<MultipartFile>,
    ): List<String>

    fun deleteAllByUrls(
        bucketFolderType: BucketFolderType,
        deleteTargetUrls: List<String>,
    )
}

package com.piikii.application.port.output.objectstorage

import com.piikii.application.domain.image.ImageFolderType
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.Future

interface ObjectStoragePort {
    fun uploadAll(
        imageFolderType: ImageFolderType,
        multipartFiles: List<MultipartFile>,
    ): Future<List<String>>

    fun updateAllByUrls(
        imageFolderType: ImageFolderType,
        deleteTargetUrls: List<String>,
        newMultipartFiles: List<MultipartFile>,
    ): Future<List<String>>

    fun deleteAllByUrls(
        imageFolderType: ImageFolderType,
        deleteTargetUrls: List<String>,
    )
}

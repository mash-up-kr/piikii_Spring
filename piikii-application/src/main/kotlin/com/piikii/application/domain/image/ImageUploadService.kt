package com.piikii.application.domain.image

import com.piikii.application.port.input.ImageUploadUseCase
import com.piikii.application.port.output.objectstorage.ObjectStoragePort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageUploadService(
    private val objectStoragePort: ObjectStoragePort,
) : ImageUploadUseCase {
    override fun upload(
        images: List<MultipartFile>?,
        imageFolderType: ImageFolderType,
    ): List<String> {
        return images?.let {
            objectStoragePort.uploadAll(
                imageFolderType = imageFolderType,
                multipartFiles = it,
            ).get()
        } ?: emptyList()
    }
}

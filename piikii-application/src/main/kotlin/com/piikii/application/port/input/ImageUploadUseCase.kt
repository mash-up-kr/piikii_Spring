package com.piikii.application.port.input

import com.piikii.application.domain.image.ImageFolderType
import org.springframework.web.multipart.MultipartFile

interface ImageUploadUseCase {
    fun upload(
        images: List<MultipartFile>?,
        imageFolderType: ImageFolderType,
    ): List<String>
}

package com.piikii.application.port.output.objectstorage

import org.springframework.web.multipart.MultipartFile

interface ObjectStoragePort {
    fun upload(
        uploadType: UploadType,
        multipartFile: MultipartFile,
    ): String
}

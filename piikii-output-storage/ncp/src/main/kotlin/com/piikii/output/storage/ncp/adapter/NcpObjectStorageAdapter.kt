package com.piikii.output.storage.ncp.adapter

import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.piikii.application.domain.image.ImageFolderType
import com.piikii.application.port.output.objectstorage.ObjectStoragePort
import com.piikii.output.storage.ncp.config.NcpProperties
import com.piikii.output.storage.ncp.config.StorageConfig
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@Component
class NcpObjectStorageAdapter(
    private val ncpProperties: NcpProperties,
    private val storageConfig: StorageConfig,
) : ObjectStoragePort {
    @Async("AsyncStorageExecutor")
    override fun uploadAll(
        imageFolderType: ImageFolderType,
        multipartFiles: List<MultipartFile>,
    ): Future<List<String>> {
        val contentUrls = mutableListOf<String>()

        for (multipartFile in multipartFiles) {
            val folderName = getFolderName(imageFolderType)
            val fileName = getFileName(multipartFile)
            val objectKey = createObjectKey(folderName, fileName)

            val objectMetadata =
                ObjectMetadata().apply {
                    contentType = multipartFile.contentType
                    contentLength = multipartFile.size
                }

            val inputStream = multipartFile.inputStream
            val putObjectRequest = createPutObjectRequest(objectKey, inputStream, objectMetadata)
            storageConfig.storageClient().putObject(putObjectRequest)
            contentUrls.add(ncpProperties.bucket.path + objectKey)
        }
        return CompletableFuture.completedFuture(contentUrls)
    }

    @Async("TaskExecutorForExternalStorage")
    override fun updateAllByUrls(
        imageFolderType: ImageFolderType,
        deleteTargetUrls: List<String>,
        newMultipartFiles: List<MultipartFile>,
    ): Future<List<String>> {
        deleteAllByUrls(imageFolderType, deleteTargetUrls)
        return uploadAll(imageFolderType, newMultipartFiles)
    }

    @Async("TaskExecutorForExternalStorage")
    override fun deleteAllByUrls(
        imageFolderType: ImageFolderType,
        deleteTargetUrls: List<String>,
    ) {
        val storageClient = storageConfig.storageClient()
        deleteTargetUrls.map {
            storageClient.deleteObject(
                DeleteObjectRequest(ncpProperties.bucket.name, it.substringAfter(ncpProperties.bucket.path)),
            )
        }
    }

    private fun getFileName(multipartFile: MultipartFile) = "${multipartFile.originalFilename}"

    private fun getFolderName(imageFolderType: ImageFolderType): String {
        return when (imageFolderType) {
            ImageFolderType.ROOM -> ncpProperties.bucket.folder.roomFolder
            ImageFolderType.PLACE -> ncpProperties.bucket.folder.placeFolder
        }
    }

    private fun createObjectKey(
        folderName: String,
        fileName: String,
    ) = "$folderName/$fileName"

    private fun createPutObjectRequest(
        objectKey: String,
        inputStream: InputStream,
        objectMetadata: ObjectMetadata,
    ) = PutObjectRequest(ncpProperties.bucket.name, objectKey, inputStream, objectMetadata).withCannedAcl(
        CannedAccessControlList.PublicReadWrite,
    )
}

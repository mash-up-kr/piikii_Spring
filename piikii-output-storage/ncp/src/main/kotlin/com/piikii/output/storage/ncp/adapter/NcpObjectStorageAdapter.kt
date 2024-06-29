package com.piikii.output.storage.ncp.adapter

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.piikii.application.port.output.objectstorage.ObjectStoragePort
import com.piikii.application.port.output.objectstorage.UploadType
import com.piikii.output.storage.ncp.config.NcpProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Component
class NcpObjectStorageAdapter(
    private val ncpProperties: NcpProperties,
) : ObjectStoragePort {
    override fun upload(
        uploadType: UploadType,
        multipartFile: MultipartFile,
    ): String {
        val s3 =
            AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                    AwsClientBuilder.EndpointConfiguration(
                        OBJECT_STORAGE_BASE_ENDPOINT,
                        REGION_NAME,
                    ),
                )
                .withCredentials(
                    AWSStaticCredentialsProvider(
                        BasicAWSCredentials(
                            ncpProperties.key.access,
                            ncpProperties.key.secret,
                        ),
                    ),
                )
                .build()

        val folderName = getFolderName(uploadType)
        val fileName = getFileName(multipartFile)
        val objectKey = createObjectKey(folderName, fileName)

        val objectMetadata =
            ObjectMetadata().apply {
                contentType = multipartFile.contentType
                contentLength = multipartFile.size
            }

        val inputStream = multipartFile.inputStream
        val putObjectRequest = createPutObjectRequest(objectKey, inputStream, objectMetadata)

        s3.putObject(putObjectRequest)

        return "$OBJECT_STORAGE_BASE_ENDPOINT/${ncpProperties.bucket.name}/$objectKey"
    }

    private fun getFileName(multipartFile: MultipartFile) = "${multipartFile.originalFilename}"

    private fun getFolderName(uploadType: UploadType): String {
        return when (uploadType) {
            UploadType.ROOM -> ncpProperties.bucket.folder.name.roomFolder
            UploadType.PLACE -> ncpProperties.bucket.folder.name.placeFolder
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
    ) = PutObjectRequest(ncpProperties.bucket.name, objectKey, inputStream, objectMetadata)

    companion object {
        private const val REGION_NAME = "kr-standard"
        private const val OBJECT_STORAGE_BASE_ENDPOINT = "https://kr.object.ncloudstorage.com"
    }
}

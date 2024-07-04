package com.piikii.output.storage.ncp.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Configuration

@Configuration
class StorageConfig(
    private val ncpProperties: NcpProperties,
) {
    fun storageClient(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
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
    }
}

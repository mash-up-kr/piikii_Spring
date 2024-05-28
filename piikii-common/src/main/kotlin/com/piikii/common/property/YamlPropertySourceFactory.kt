package com.piikii.common.property

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory


class YamlPropertySourceFactory : PropertySourceFactory {
    override fun createPropertySource(
        name: String?,
        encodedResource: EncodedResource
    ): org.springframework.core.env.PropertySource<*> =
        PropertiesPropertySource(
            encodedResource.resource.filename!!,
            YamlPropertiesFactoryBean().also { it.setResources(encodedResource.resource) }.getObject()!!
        )
}

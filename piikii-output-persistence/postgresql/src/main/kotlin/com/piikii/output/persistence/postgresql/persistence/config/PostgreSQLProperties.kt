package com.piikii.output.persistence.postgresql.persistence.config

import com.piikii.common.property.YamlPropertySourceFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(
    ignoreResourceNotFound = true,
    value = ["classpath:/database-config/application-\${spring.profiles.active}.yml"],
    factory = YamlPropertySourceFactory::class
)
class PostgreSQLProperties

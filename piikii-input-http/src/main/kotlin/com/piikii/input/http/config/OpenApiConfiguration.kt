package com.piikii.input.http.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean
    fun openApi(): OpenAPI {
        val info =
            Info().title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)

        val server =
            Server()
                .url("https://api.piikii.co.kr")
                .url("localhost:8080")

        return OpenAPI()
            .components(Components())
            .info(info)
            .servers(listOf(server))
    }

    companion object {
        const val API_NAME = "PIIKII-API"
        const val API_VERSION = "v1"
        const val API_DESCRIPTION = "PIIKII-API description"
    }
}

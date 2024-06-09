package com.piikii.input.http.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean
    fun openApi(): OpenAPI {
        val info = Info().title(SwaggerConstant.API_NAME)
            .version(SwaggerConstant.API_VERSION)
            .description(SwaggerConstant.API_DESCRIPTION)

        return OpenAPI()
            .components(Components())
            .info(info)
    }

}

object SwaggerConstant {
    const val API_NAME = "PIIKII-API"
    const val API_VERSION = "v1"
    const val API_DESCRIPTION = "PIIKII-API description"
}

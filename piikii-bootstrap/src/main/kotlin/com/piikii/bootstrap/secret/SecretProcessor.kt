package com.piikii.bootstrap.secret

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

class SecretProcessor : EnvironmentPostProcessor {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication?) {
        val secrets = getSecrets(environment)
        val piikiiSecretProperties = MapPropertySource(
            PIIKII_SECRET_PROPERTY_NAME,
            secrets.associate { it.secretKey to it.secretValue }
        )
        environment.propertySources.addLast(piikiiSecretProperties)
    }

    private fun getSecrets(environment: ConfigurableEnvironment): List<Secret> {
        val activeProfiles = environment.activeProfiles
        log.info("### ACTIVE PROFILES: ${activeProfiles.joinToString(PROFILE_SEPARATOR)} ###")

        val secretToken = environment.getPropertyOrThrow(PROPERTY_SECRET_MANAGER_TOKEN_NAME)
        val workspaceId = environment.getPropertyOrThrow(PROPERTY_SECRET_MANAGER_WORKSPACE_NAME)
        val currentEnvironment = activeProfiles.first { SUPPORT_PROFILES.contains(it) }
        val secrets = secretManagerRestClient(secretToken).get()
            .uri("/secrets/raw?workspaceId=$workspaceId&environment=$currentEnvironment")
            .retrieve()
            .body(SecretResponse::class.java)?.secrets ?: emptyList()
        return secrets
    }

    private fun ConfigurableEnvironment.getPropertyOrThrow(name: String): String {
        return this.getProperty(name) ?: throw PiikiiException(ExceptionCode.SECRET_MANAGER_CONFIG_NOT_SET)
    }

    private fun secretManagerRestClient(secretToken: String) = RestClient.builder()
        .defaultHeaders { it.add(HttpHeaders.AUTHORIZATION, "$BEARER_TOKEN_PREFIX $secretToken") }
        .baseUrl(SECRET_MANAGER_URL)
        .build()

    companion object {
        const val PROFILE_SEPARATOR = ","

        const val PIIKII_SECRET_PROPERTY_NAME = "piikiiApplicationSecretProperty"
        const val PROPERTY_SECRET_MANAGER_TOKEN_NAME = "SECRET_MANAGER_TOKEN"
        const val PROPERTY_SECRET_MANAGER_WORKSPACE_NAME = "SECRET_MANAGER_WORKSPACE"

        const val BEARER_TOKEN_PREFIX = "Bearer"
        const val SECRET_MANAGER_URL = "https://app.infisical.com/api/v3"

        val SUPPORT_PROFILES = setOf("local", "dev", "prod")
    }

}

class SecretResponse(
    val secrets: List<Secret>
)

class Secret(
    val id: String,
    val workspace: String,
    val environment: String,
    val version: Long,
    val secretKey: String,
    val secretValue: String,
)

package com.piikii.output.web.lemon.util

import com.piikii.output.web.lemon.config.LemonProperties
import org.springframework.stereotype.Component

@Component
class SaltAdditive(
    private val lemonProperties: LemonProperties,
) {
    fun execute(url: String): String {
        val regex = """(${lemonProperties.baseUrl})(\d+)""".toRegex()
        return regex.replace(url) { matchResult ->
            val (baseUrl, id) = matchResult.destructured
            "$baseUrl${lemonProperties.salt}$id"
        }
    }
}

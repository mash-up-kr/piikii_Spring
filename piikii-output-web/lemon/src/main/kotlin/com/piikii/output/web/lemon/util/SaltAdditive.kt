package com.piikii.output.web.lemon.util

import org.springframework.stereotype.Component

@Component
class SaltAdditive {
    companion object {
        private const val SALT = "main/v/"
    }

    fun execute(url: String): String {
        val regex = """(https://place\.map\.kakao\.com/m/)(\d+)""".toRegex()
        return regex.replace(url) { matchResult ->
            val (baseUrl, id) = matchResult.destructured
            "$baseUrl$SALT$id"
        }
    }
}

package com.piikii.output.web.lemon.parser

import com.piikii.output.web.lemon.config.LemonProperties
import org.springframework.stereotype.Component

@Component
class LemonPlaceIdParser(
    private val properties: LemonProperties,
) {
    private val patternRegex: Regex = "${properties.url.regex.web}$PLACE_ID_REGEX".toRegex()
    private val parseRegex: Regex = "${properties.url.regex.web}($PLACE_ID_REGEX)".toRegex()

    fun isAutoCompleteSupportedUrl(url: String): Boolean {
        return patternRegex.matches(url)
    }

    fun parse(url: String): String? {
        if (!isAutoCompleteSupportedUrl(url)) {
            return null
        }
        return parseRegex.find(url)?.groupValues?.get(1)
    }

    companion object {
        const val PLACE_ID_REGEX = "\\d+"
    }
}

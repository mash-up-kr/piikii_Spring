package com.piikii.output.web.lemon.parser

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.web.lemon.config.LemonProperties
import org.springframework.stereotype.Component

@Component
class LemonOriginMapIdParser(
    properties: LemonProperties,
) {
    private val patternRegex: Regex = "${properties.url.regex.web}$PLACE_ID_REGEX".toRegex()
    private val parseRegex: Regex = "${properties.url.regex.web}($PLACE_ID_REGEX)".toRegex()

    fun isAutoCompleteSupportedUrl(url: String): Boolean {
        return patternRegex.matches(url)
    }

    fun parseOriginMapId(url: String): OriginMapId? {
        if (!isAutoCompleteSupportedUrl(url)) {
            return null
        }
        return parseRegex.find(url)
            ?.groupValues
            ?.getOrNull(1)
            ?.toLongOrNull()
            ?.let { OriginMapId.of(id = it, origin = Origin.LEMON) }
    }

    companion object {
        const val PLACE_ID_REGEX = "\\d+"
    }
}

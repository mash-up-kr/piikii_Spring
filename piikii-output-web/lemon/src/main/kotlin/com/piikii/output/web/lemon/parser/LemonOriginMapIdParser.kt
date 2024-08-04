package com.piikii.output.web.lemon.parser

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.web.lemon.config.LemonProperties
import org.springframework.stereotype.Component

@Component
class LemonOriginMapIdParser(
    properties: LemonProperties,
) {
    private val regexes: List<Regex> =
        listOf(
            "${properties.url.regex.web}($ORIGIN_MAP_IP_REGEX)".toRegex(),
            "${properties.url.regex.mobileWeb}($ORIGIN_MAP_IP_REGEX)".toRegex(),
        )

    fun isAutoCompleteSupportedUrl(url: String): Boolean = regexes.any { it.matches(url) }

    fun parseOriginMapId(url: String): OriginMapId? {
        return regexes.firstOrNull { it.matches(url) }
            ?.find(url)
            ?.groupValues
            ?.getOrNull(1)
            ?.toLongOrNull()
            ?.let { OriginMapId.of(id = LongTypeId(it), origin = Origin.LEMON) }
    }

    companion object {
        const val ORIGIN_MAP_IP_REGEX = "\\d+"
    }
}

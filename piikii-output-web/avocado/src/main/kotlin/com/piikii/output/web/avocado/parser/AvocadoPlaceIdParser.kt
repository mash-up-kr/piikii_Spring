package com.piikii.output.web.avocado.parser

import com.piikii.output.web.avocado.config.AvocadoProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class AvocadoPlaceIdParserStrategy(private val parsers: List<AvocadoPlaceIdParser>) {
    fun getParserBySupportedUrl(url: String): AvocadoPlaceIdParser? {
        return parsers.find { it.pattern().matches(url) }
    }
}

interface AvocadoPlaceIdParser {
    fun pattern(): Regex

    fun parsePlaceId(url: String): String?
}

@Component
class MapPlaceIdParser(properties: AvocadoProperties) : AvocadoPlaceIdParser {
    private val patternRegex: Regex = "${properties.url.regex.web}$PLACE_ID_REGEX".toRegex()
    private val parseRegex: Regex = "${properties.url.regex.web}($PLACE_ID_REGEX)".toRegex()

    override fun pattern(): Regex {
        return patternRegex
    }

    override fun parsePlaceId(url: String): String? {
        return parseRegex.find(url)?.groupValues?.get(1)
    }

    companion object {
        const val PLACE_ID_REGEX = "\\d+"
    }
}

@Component
class SharePlaceIdParser(
    properties: AvocadoProperties,
) : AvocadoPlaceIdParser {
    private val regex: Regex = properties.url.regex.share.toRegex()
    private val idParameterRegex: Regex = "id=(\\d+)".toRegex()
    private val client: RestClient = RestClient.builder().build()

    override fun pattern(): Regex {
        return regex
    }

    override fun parsePlaceId(url: String): String? {
        val response =
            client.get().uri(url)
                .retrieve()
                .toEntity(Map::class.java)
        if (response.statusCode.is3xxRedirection && response.headers.location != null) {
            val extractedId = idParameterRegex.find(response.headers.location.toString())
            return extractedId?.groupValues?.get(1)
        }
        return null
    }
}

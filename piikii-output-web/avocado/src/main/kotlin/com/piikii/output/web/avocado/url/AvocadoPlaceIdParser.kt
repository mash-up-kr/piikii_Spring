package com.piikii.output.web.avocado.url

import com.piikii.output.web.avocado.config.AvocadoProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class AvocadoPlaceIdParserStrategy(private val parsers: List<AvocadoPlaceIdParser>) {
    fun parse(url: String): String? {
        val parser = parsers.find { it.pattern().matches(url) }
        return parser?.parse(url)
    }
}

interface AvocadoPlaceIdParser {
    fun pattern(): Regex

    fun parse(url: String): String?
}

@Component
class MapPlaceIdParser(properties: AvocadoProperties) : AvocadoPlaceIdParser {
    private val regex: Regex = properties.url.webRegex()

    override fun pattern(): Regex {
        return regex
    }

    override fun parse(url: String): String? {
        return "map"
    }
}

@Component
class SharePlaceIdParser(
    properties: AvocadoProperties,
) : AvocadoPlaceIdParser {
    private val regex: Regex = properties.url.shareRegex()
    private val idParameterRegex: Regex = "id=(\\d+)".toRegex()
    private val client: RestClient = RestClient.builder().build()

    override fun pattern(): Regex {
        return regex
    }

    override fun parse(url: String): String? {
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

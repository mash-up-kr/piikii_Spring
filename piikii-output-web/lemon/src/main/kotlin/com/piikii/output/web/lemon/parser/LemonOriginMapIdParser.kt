package com.piikii.output.web.lemon.parser

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.web.lemon.config.LemonProperties
import com.piikii.output.web.lemon.parser.LemonOriginMapIdParser.Companion.ANY_REGEX
import com.piikii.output.web.lemon.parser.LemonOriginMapIdParser.Companion.NUMBER_REGEX
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class LemonOriginMapIdParserStrategy(private val parsers: List<LemonOriginMapIdParser>) {
    fun getParserBySupportedUrl(url: String): LemonOriginMapIdParser? =
        parsers.firstOrNull { it.getParserBySupportedUrl(url) != null }
}

interface LemonOriginMapIdParser {
    fun getParserBySupportedUrl(url: String): LemonOriginMapIdParser?

    fun parseOriginMapId(url: String): OriginMapId?

    fun MatchResult?.parseFromMatchResult(): OriginMapId? =
        this?.groupValues?.getOrNull(1)?.toLongOrNull()?.let {
            OriginMapId.of(id = LongTypeId(it), origin = Origin.LEMON)
        }

    companion object {
        const val NUMBER_REGEX = "\\d+"
        const val ANY_REGEX = ".+"
    }
}

@Component
class WebUrlParser(properties: LemonProperties) : LemonOriginMapIdParser {
    private val regex = "${properties.url.regex.web}($NUMBER_REGEX)".toRegex()

    override fun getParserBySupportedUrl(url: String): LemonOriginMapIdParser? = takeIf { regex.matches(url) }

    override fun parseOriginMapId(url: String): OriginMapId? = regex.find(url).parseFromMatchResult()
}

@Component
class MobileWebUrlParser(properties: LemonProperties) : LemonOriginMapIdParser {
    private val regex = "${properties.url.regex.mobileWeb}($NUMBER_REGEX)".toRegex()

    override fun getParserBySupportedUrl(url: String): LemonOriginMapIdParser? = takeIf { regex.matches(url) }

    override fun parseOriginMapId(url: String): OriginMapId? = regex.find(url).parseFromMatchResult()
}

@Component
class MobileAppUrlParser(properties: LemonProperties) : LemonOriginMapIdParser {
    private val regex = "^${properties.url.regex.mobileApp}($ANY_REGEX)$".toRegex()
    private val idParameterRegex = "itemId=(\\d+)".toRegex()
    private val client: RestClient = RestClient.builder().build()

    override fun getParserBySupportedUrl(url: String): LemonOriginMapIdParser? = takeIf { regex.matches(url) }

    override fun parseOriginMapId(url: String): OriginMapId? {
        val response = client.get().uri(url).retrieve().toEntity(String::class.java)
        return response.headers.location?.toString()?.let { location ->
            idParameterRegex.find(location).parseFromMatchResult()
        }.takeIf { response.statusCode.is3xxRedirection }
    }
}

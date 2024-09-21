package com.piikii.output.web.avocado.parser

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.web.avocado.config.AvocadoProperties
import com.piikii.output.web.avocado.parser.AvocadoOriginMapIdParser.Companion.ORIGIN_MAP_IP_REGEX
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class AvocadoOriginMapIdParserStrategy(private val parsers: List<AvocadoOriginMapIdParser>) {
    fun getParserBySupportedUrl(url: String): AvocadoOriginMapIdParser? {
        return parsers.firstOrNull { it.getParserBySupportedUrl(url) != null }
    }
}

interface AvocadoOriginMapIdParser {
    fun getParserBySupportedUrl(url: String): AvocadoOriginMapIdParser?

    fun parseOriginMapId(url: String): OriginMapId?

    /**
     * Regex를 이용해 OriginMapId 반환
     * - Regex Match 결과로부터 첫 번째 값을 꺼내 OriginMapId 변환 및 반환
     *
     * @return OriginMapId
     */
    fun MatchResult?.parseFromMatchResult(): OriginMapId? {
        return this?.groupValues
            ?.getOrNull(1)
            ?.toLongOrNull()
            ?.let { OriginMapId.of(id = LongTypeId(it), origin = Origin.AVOCADO) }
    }

    companion object {
        const val ORIGIN_MAP_IP_REGEX = "\\d+"
    }
}

@Component
class MapUrlIdParser(properties: AvocadoProperties) : AvocadoOriginMapIdParser {
    private val regexes: List<Regex> =
        listOf(
            "${properties.url.regex.web}($ORIGIN_MAP_IP_REGEX)".toRegex(),
            "${properties.url.regex.mobileWeb}($ORIGIN_MAP_IP_REGEX)/home".toRegex(),
        )

    override fun getParserBySupportedUrl(url: String): AvocadoOriginMapIdParser? =
        takeIf { regexes.any { regex -> regex.matches(url) } }

    override fun parseOriginMapId(url: String): OriginMapId? {
        return regexes.first { it.matches(url) }.find(url).parseFromMatchResult()
    }
}

@Component
class ShareUrlIdParser(
    properties: AvocadoProperties,
) : AvocadoOriginMapIdParser {
    private val regex: Regex = properties.url.regex.share.toRegex()
    private val operateRegexList: List<Regex> =
        mutableListOf(
            "place/(\\d+)".toRegex(),
            "id=(\\d+)".toRegex(),
        )

    private val client: RestClient = RestClient.builder().build()

    override fun getParserBySupportedUrl(url: String): AvocadoOriginMapIdParser? = takeIf { regex.matches(url) }

    override fun parseOriginMapId(url: String): OriginMapId? {
        val response =
            client.get().uri(url)
                .retrieve()
                .toEntity(Map::class.java)
        if (response.statusCode.is3xxRedirection && response.headers.location != null) {
            return operateRegexList.firstNotNullOfOrNull { regex ->
                regex.find(response.headers.location.toString())
                    ?.groupValues?.getOrNull(1)?.let { OriginMapId(it) }
            }
        }
        return null
    }
}

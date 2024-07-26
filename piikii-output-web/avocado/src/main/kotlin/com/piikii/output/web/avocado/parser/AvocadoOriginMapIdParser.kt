package com.piikii.output.web.avocado.parser

import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.web.avocado.config.AvocadoProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class AvocadoOriginMapIdParserStrategy(private val parsers: List<AvocadoOriginMapIdParser>) {
    fun getParserBySupportedUrl(url: String): AvocadoOriginMapIdParser? {
        return parsers.find { it.pattern().matches(url) }
    }
}

interface AvocadoOriginMapIdParser {
    fun pattern(): Regex

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
            ?.let { OriginMapId(it) }
    }
}

@Component
class MapUrlIdParser(properties: AvocadoProperties) : AvocadoOriginMapIdParser {
    private val patternRegex: Regex = "${properties.url.regex.web}$PLACE_ID_REGEX".toRegex()
    private val parseRegex: Regex = "${properties.url.regex.web}($PLACE_ID_REGEX)".toRegex()

    override fun pattern(): Regex {
        return patternRegex
    }

    override fun parseOriginMapId(url: String): OriginMapId? {
        return parseRegex.find(url).parseFromMatchResult()
    }

    companion object {
        const val PLACE_ID_REGEX = "\\d+"
    }
}

@Component
class ShareUrlIdParser(
    properties: AvocadoProperties,
) : AvocadoOriginMapIdParser {
    private val regex: Regex = properties.url.regex.share.toRegex()
    private val idParameterRegex: Regex = "id=(\\d+)".toRegex()
    private val client: RestClient = RestClient.builder().build()

    override fun pattern(): Regex {
        return regex
    }

    override fun parseOriginMapId(url: String): OriginMapId? {
        val response =
            client.get().uri(url)
                .retrieve()
                .toEntity(Map::class.java)
        if (response.statusCode.is3xxRedirection && response.headers.location != null) {
            return idParameterRegex.find(response.headers.location.toString())
                .parseFromMatchResult()
        }
        return null
    }
}

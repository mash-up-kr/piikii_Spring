package com.piikii.output.web.avocado.url

import com.piikii.output.web.avocado.config.AvocadoProperties
import org.springframework.stereotype.Component

class AvocadoUrlParserStrategy(private val parsers: List<AvocadoUrlParser>) {
    fun parse(url: String): String? {
        val parser = parsers.find { it.pattern().matches(url) }
        return parser?.parse(url)
    }
}

interface AvocadoUrlParser {
    fun pattern(): Regex

    fun parse(url: String): String?
}

@Component
class MapUrlParser(properties: AvocadoProperties) : AvocadoUrlParser {
    private val regex: Regex = properties.url.webRegex()

    override fun pattern(): Regex {
        return regex
    }

    override fun parse(url: String): String? {
        return "map"
    }
}

@Component
class ShareUrlParser(properties: AvocadoProperties) : AvocadoUrlParser {
    private val regex: Regex = properties.url.shareRegex()

    override fun pattern(): Regex {
        return regex
    }

    override fun parse(url: String): String? {
        return "share"
    }
}

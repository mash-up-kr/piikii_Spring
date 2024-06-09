package com.piikii.application.domain.generic

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.piikii.application.consts.THUMBNAIL_LINK_SEPARATOR
import java.io.Serializable

class ThumbnailLinks(
    @JsonIgnore
    val contents: String?
) : Serializable {

    constructor(contentList: List<String>) : this(contentList.joinToString(THUMBNAIL_LINK_SEPARATOR))

    @get:JsonProperty("contents")
    val convertToList: List<String>
        get() = contents?.split(THUMBNAIL_LINK_SEPARATOR) ?: emptyList()
}

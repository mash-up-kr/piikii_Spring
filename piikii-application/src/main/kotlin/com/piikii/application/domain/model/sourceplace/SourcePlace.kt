package com.piikii.application.domain.model.sourceplace

import com.piikii.application.domain.model.enums.Source

class SourcePlace(
    val originMapId: Long,
    val url: String,
    val thumbnailLinks: List<String>? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source
) {
}



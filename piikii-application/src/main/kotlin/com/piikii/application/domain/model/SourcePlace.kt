package com.piikii.application.domain.model

class SourcePlace(
    val originMapId: Long,
    val url: String,
    val thumbnailLink: List<String>? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: String? = null
) {
}



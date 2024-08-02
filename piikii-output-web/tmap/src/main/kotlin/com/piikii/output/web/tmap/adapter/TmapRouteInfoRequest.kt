package com.piikii.output.web.tmap.adapter

class TmapRouteInfoRequest(
    val startX: Double,
    val startY: Double,
    val endX: Double,
    val endY: Double,
    val startName: String? = "default",
    val endName: String? = "default",
)

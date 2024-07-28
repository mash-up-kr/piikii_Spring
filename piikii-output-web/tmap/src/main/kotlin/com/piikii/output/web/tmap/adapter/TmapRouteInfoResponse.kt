package com.piikii.output.web.tmap.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.piikii.application.domain.course.Distance

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmapRouteInfoResponse(
    val type: String,
    val features: List<Feature>,
) {
    fun toDistance(): Distance {
        val featureWitTotalDistance = features.firstOrNull { it.properties.totalDistance != null }
        return if (featureWitTotalDistance != null) {
            Distance(
                totalDistance = featureWitTotalDistance.properties.totalDistance,
                totalTime = featureWitTotalDistance.properties.totalTime,
            )
        } else {
            Distance.empty()
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feature(
        val type: String,
        val geometry: Geometry,
        val properties: Properties,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Geometry(
        val type: String,
        val coordinates: List<Any>,
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Properties(
        val totalDistance: Int? = null,
        val totalTime: Int? = null,
        val index: Int,
        val pointIndex: Int? = null,
        val name: String? = null,
        val description: String,
        val direction: String? = null,
        val nearPoiName: String? = null,
        val nearPoiX: String? = null,
        val nearPoiY: String? = null,
        val intersectionName: String? = null,
        val facilityType: String,
        val facilityName: String? = null,
        val turnType: Int? = null,
        val pointType: String? = null,
        val lineIndex: Int? = null,
        val distance: Int? = null,
        val time: Int? = null,
        val roadType: Int? = null,
        val categoryRoadType: Int? = null,
    )
}

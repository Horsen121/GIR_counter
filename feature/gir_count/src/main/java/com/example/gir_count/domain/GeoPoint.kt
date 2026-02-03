package com.example.gir_count.domain

@JvmInline
value class Latitude(val degrees: Double) {
    init {
        require(degrees in -90.0..90.0) { "Latitude must be between -90 and 90" }
    }
}

@JvmInline
value class Longitude(val degrees: Double) {
    init {
        require(degrees in -180.0..180.0) { "Longitude must be between -180 and 180" }
    }
}

data class GeoPoint(
    val lat: Latitude,
    val lon: Longitude,
)
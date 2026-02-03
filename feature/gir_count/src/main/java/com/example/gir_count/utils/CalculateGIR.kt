package com.example.gir_count.utils

import com.example.gir_count.domain.GeoPoint
import com.example.gir_count.domain.Green
import com.example.gir_count.domain.GreenForm
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private const val EARTH_RADIUS_METERS = 6371000.0

internal fun haversine(p1: GeoPoint, p2: GeoPoint): Double {
    val lat1 = Math.toRadians(p1.lat.degrees)
    val lon1 = Math.toRadians(p1.lon.degrees)
    val lat2 = Math.toRadians(p2.lat.degrees)
    val lon2 = Math.toRadians(p2.lon.degrees)

    val dLat = lat2 - lat1
    val dLon = lon2 - lon1

    val a = sin(dLat / 2).pow(2) +
            cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
    val c = 2 * asin(sqrt(a))

    return EARTH_RADIUS_METERS * c
}

fun calculateGIR(green: Green, shot: GeoPoint): Boolean {
    return when(green.form) {
        GreenForm.ROUND -> {
            val radius = haversine(green.middle, green.front)
            val dist = haversine(green.middle, shot)

            dist <= radius + 1.0
        }
        GreenForm.SQUARE -> {
            val half = abs(green.front.lat.degrees - green.middle.lat.degrees)

            val minLat = green.middle.lat.degrees - half
            val maxLat = green.middle.lat.degrees + half
            val minLon = green.middle.lon.degrees - half
            val maxLon = green.middle.lon.degrees + half

            shot.lat.degrees in minLat..maxLat &&
                shot.lon.degrees in minLon..maxLon
        }
    }
}
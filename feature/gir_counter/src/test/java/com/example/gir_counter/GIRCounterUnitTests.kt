package com.example.gir_counter

import com.example.gir_counter.domain.GeoPoint
import com.example.gir_counter.domain.Green
import com.example.gir_counter.domain.GreenForm
import com.example.gir_counter.domain.Latitude
import com.example.gir_counter.domain.Longitude
import com.example.gir_counter.utils.calculateGIR
import com.example.gir_counter.utils.haversine
import org.junit.Test

class GIRCalculatorTest {
    val center50 = GeoPoint(Latitude(50.0), Longitude(0.0))
    val greenRound = Green(
        GreenForm.ROUND,
        GeoPoint(Latitude(50.0001), Longitude(0.0)),
        center50,
        GeoPoint(Latitude(49.9999), Longitude(0.0))
        )
    val greenSquare = Green(
        GreenForm.SQUARE,
        GeoPoint(Latitude(50.0001), Longitude(0.0)),
        center50,
        GeoPoint(Latitude(49.9999), Longitude(0.0))
    )

    @Test
    fun `haversine zero distance returns 0`() {
        val dist = haversine(center50, center50)
        assert(dist == 0.0)
    }

    @Test
    fun `isInsideCircle point on edge is inside`() {
        val shot = GeoPoint(Latitude(50.0001), Longitude(0.0)) // ~11м
        assert(calculateGIR(greenRound, shot))
    }

    @Test
    fun `isInsideCircle point inside is true`() {
        val shot = GeoPoint(Latitude(50.00007), Longitude(0.00003)) // ~11м
        assert(calculateGIR(greenRound, shot))
    }

    @Test
    fun `isInsideCircle point outside returns false`() {
        val outside = GeoPoint(Latitude(50.0002), Longitude(0.0))
        assert(!calculateGIR(greenRound, outside))
    }

    @Test
    fun `isInsideSquare center point returns true`() {
        val shot = GeoPoint(Latitude(50.0), Longitude(0.0))
        assert(calculateGIR(greenSquare, shot))
    }

    @Test
    fun `isInsideSquare corner point returns true`() {
        val shot = GeoPoint(Latitude(50.0001), Longitude(0.00005))
        assert(calculateGIR(greenSquare, shot))
    }

    @Test
    fun `isInsideSquare inside point returns true`() {
        val shot = GeoPoint(Latitude(50.00), Longitude(0.00001))
        assert(calculateGIR(greenSquare, shot))
    }

    @Test
    fun `isInsideSquare outside bounds returns false`() {
        val shot = GeoPoint(Latitude(50.0002), Longitude(0.0))
        assert(!calculateGIR(greenSquare, shot))
    }
}
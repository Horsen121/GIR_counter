package com.example.gir_counter.domain

enum class GreenForm {
    ROUND,
    SQUARE
}

data class Green(
    val form: GreenForm,
    val front: GeoPoint,
    val middle: GeoPoint,
    val back: GeoPoint,
)

package com.example.gir_count.domain

import android.content.Context
import com.example.gir_count.R

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

fun greenStringList(context: Context): List<String> {
    return listOf(
        context.getString(R.string.gir_counter_par3),
        context.getString(R.string.gir_counter_par4),
    )
}
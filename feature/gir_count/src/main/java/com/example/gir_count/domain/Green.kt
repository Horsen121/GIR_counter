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
        context.getString(R.string.gir_counter_green_round),
        context.getString(R.string.gir_counter_green_square),
    )
}

fun GreenForm.string(): Int {
    return when(this) {
        GreenForm.ROUND -> { R.string.gir_counter_green_round }
        else -> R.string.gir_counter_green_square
    }
}
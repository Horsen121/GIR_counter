package com.example.gir_count.domain

import android.content.Context
import com.example.gir_count.R

enum class Par {
    PAR_3,
    PAR_4,
    PAR_5
}

fun Par.toInt(): Int {
    return when(this) {
        Par.PAR_3 -> 1
        Par.PAR_4 -> 2
        Par.PAR_5 -> 3
    }
}

fun parStringList(context: Context): List<String> {
    return listOf(
        context.getString(R.string.gir_counter_par3),
        context.getString(R.string.gir_counter_par4),
        context.getString(R.string.gir_counter_par5),
    )
}
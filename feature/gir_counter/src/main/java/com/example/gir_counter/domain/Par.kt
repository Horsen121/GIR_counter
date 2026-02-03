package com.example.gir_counter.domain

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
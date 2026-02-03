package com.example.gir_count.presentation

import com.example.gir_count.domain.GeoPoint
import com.example.gir_count.domain.Green
import com.example.gir_count.domain.Par

sealed interface GIRCounterState {
    data object Initial : GIRCounterState
    data class Calculation(
        val par: Par,
        val green: Green,
        val shots: List<GeoPoint>
    ) : GIRCounterState
    data class Error(val message: String) : GIRCounterState
    data class Result(val isGIR: Boolean) : GIRCounterState
}
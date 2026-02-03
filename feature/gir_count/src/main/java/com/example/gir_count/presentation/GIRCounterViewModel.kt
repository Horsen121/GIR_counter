package com.example.gir_count.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gir_count.domain.GeoPoint
import com.example.gir_count.domain.Green
import com.example.gir_count.domain.GreenForm
import com.example.gir_count.domain.Latitude
import com.example.gir_count.domain.Longitude
import com.example.gir_count.domain.Par
import com.example.gir_count.domain.greenStringList
import com.example.gir_count.domain.parStringList
import com.example.gir_count.domain.toInt
import com.example.gir_count.utils.calculateGIR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GIRCounterViewModel(
    application: Application,
): AndroidViewModel(application) {

    private val _state = MutableStateFlow<GIRCounterState>(GIRCounterState.Initial)
    val state: StateFlow<GIRCounterState> = _state.asStateFlow()

    private val _par = MutableStateFlow(Par.PAR_3)
    val parValues = parStringList(application)

    private val _green = MutableStateFlow<Green?>(null)
    val greenValues = greenStringList(application)
    private val _greenFront =  MutableStateFlow(0.0f)
    val greenFront: StateFlow<Float> = _greenFront
    private val _greenMiddle =  MutableStateFlow(0.0f)
    val greenMiddle: StateFlow<Float> = _greenMiddle
    private val _greenBack =  MutableStateFlow(0.0f)
    val greenBack: StateFlow<Float> = _greenBack

    private val _shots = MutableStateFlow<List<GeoPoint>>(emptyList())
    private val _shotLat =  MutableStateFlow(0.0f)
    val shotLat: StateFlow<Float> = _shotLat
    private val _shotLong =  MutableStateFlow(0.0f)
    val shotLong: StateFlow<Float> = _shotLong

    fun onParChange(par: Par) {
        _par.value = par
    }

    fun onGreenFormChange(green: Green) {
        _green.value = green
    }

    fun onShotAdd(shot: GeoPoint) {
        _shots.value = _shots.value.plus(shot)
    }

    fun setCalculation(fromDevice: Boolean) {
        if(fromDevice) {
            _state.value = GIRCounterState.Calculation(
                par = Par.PAR_5,
                green = Green(
                    form = GreenForm.ROUND,
                    front = GeoPoint(Latitude(50.0001), Longitude(0.0)),
                    middle = GeoPoint(Latitude(50.0), Longitude(0.0)),
                    back = GeoPoint(Latitude(49.9999), Longitude(0.0))
                ),
                shots = listOf(
                    GeoPoint(Latitude(50.0002), Longitude(0.0)),
                    GeoPoint(Latitude(50.00007), Longitude(0.00003))
                )
            )
        } else
            _state.value = GIRCounterState.Calculation(
                _par.value,
                _green.value!!,
                _shots.value
            )
    }

    fun calculate(state: GIRCounterState.Calculation) {
        viewModelScope.launch {
            _state.value = GIRCounterState.Result(
                if (state.shots.size < state.par.toInt())
                    calculateGIR(state.green, state.shots.last())
                else false
            )
        }
    }

    fun clearAll() {
        _par.value = Par.PAR_3
        _green.value = null
        _shots.value = emptyList()

        _state.value = GIRCounterState.Initial
    }
}
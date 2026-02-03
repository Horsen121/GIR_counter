package com.example.gir_count.presentation

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private val _greenForm = MutableStateFlow<GreenForm?>(null)
    val greenForm: StateFlow<GreenForm?> = _greenForm
    private val _greenFrontLat = mutableStateOf("")
    val greenFrontLat: State<String> = _greenFrontLat
    private val _greenFrontLong = mutableStateOf("")
    val greenFrontLong: State<String> = _greenFrontLong
    private val _greenMiddleLat =  mutableStateOf("")
    val greenMiddleLat: State<String> = _greenMiddleLat
    private val _greenMiddleLong =  mutableStateOf("")
    val greenMiddleLong: State<String> = _greenMiddleLong
    private val _greenBackLat =  mutableStateOf("")
    val greenBackLat: State<String> = _greenBackLat
    private val _greenBackLong =  mutableStateOf("")
    val greenBackLong: State<String> = _greenBackLong

    private val _shots = MutableStateFlow<List<GeoPoint>>(emptyList())
    private val _shotLat = mutableStateOf("")
    val shotLat: State<String> = _shotLat
    private val _shotLong =  mutableStateOf("")
    val shotLong: State<String> = _shotLong

    val isCalculationEnabled = MutableStateFlow(false)

    fun onParChange(par: Par) {
        _par.value = par
    }

    fun onFormChange(value: GreenForm) { _greenForm.value = value }
    fun onFrontLatChange(value: String) { _greenFrontLat.value = value }
    fun onFrontLongChange(value: String) { _greenFrontLong.value = value }
    fun onMiddleLatChange(value: String) { _greenMiddleLat.value = value }
    fun onMiddleLongChange(value: String) { _greenMiddleLong.value = value }
    fun onBackLatChange(value: String) { _greenBackLat.value = value }
    fun onBackLongChange(value: String) { _greenBackLong.value = value }
    fun onGreenChange(green: Green) {
        _green.value = green

        isCalculationEnabled.value = _shots.value.isNotEmpty()
    }

    fun onShotLatChange(value: String) { _shotLat.value = value }
    fun onShotLongChange(value: String) { _shotLong.value = value }
    fun onShotAdd(shot: GeoPoint) {
        _shots.value = _shots.value.plus(shot)

        isCalculationEnabled.value = _green.value != null
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
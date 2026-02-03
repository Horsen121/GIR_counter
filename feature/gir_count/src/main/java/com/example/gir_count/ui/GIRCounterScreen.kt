package com.example.gir_count.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gir_count.R
import com.example.gir_count.domain.GeoPoint
import com.example.gir_count.domain.Green
import com.example.gir_count.domain.GreenForm
import com.example.gir_count.domain.Latitude
import com.example.gir_count.domain.Longitude
import com.example.gir_count.domain.Par
import com.example.gir_count.domain.string
import com.example.gir_count.presentation.GIRCounterState
import com.example.gir_count.presentation.GIRCounterViewModel
import com.example.ui.components.BodyLargeText
import com.example.ui.components.BodyMediumText
import com.example.ui.components.CoordinateTextField
import com.example.ui.components.LabelMediumText
import com.example.ui.components.TextFieldButton
import com.example.ui.components.TextSwitch
import com.example.ui.components.TitleText
import com.example.ui.elements.BaseDropdownMenu
import com.example.ui.elements.FullScreenProgressIndicator
import org.koin.androidx.compose.koinViewModel
import kotlin.text.toDouble

@Composable
fun GIRCounterScreen(

) {
    val viewModel: GIRCounterViewModel = koinViewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isCalculationEnabled by viewModel.isCalculationEnabled.collectAsStateWithLifecycle()

    TitleText(R.string.gir_counter_screen_name)

    Crossfade(targetState = state) { currentState ->
        when (currentState) {
            is GIRCounterState.Initial -> {
                GIRCounterScreenGetData(
                    viewModel,
                    isCalculationEnabled,
                    viewModel::setCalculation
                )
            }

            is GIRCounterState.Calculation -> {
                GIRCounterScreenCalculate(
                    currentState,
                    viewModel::calculate
                )
            }

            is GIRCounterState.Result -> {
                GIRCounterScreenResult(
                    currentState,
                    viewModel::clearAll
                )
            }

            is GIRCounterState.Error -> {
                Toast.makeText(context, currentState.message, Toast.LENGTH_SHORT).show()
                Log.e("TAG", "PizzaCardScreen: ${currentState.message}")
            }
        }
    }
}

@Composable
fun GIRCounterScreenGetData(
    viewModel: GIRCounterViewModel,
    isCalculationEnabled: Boolean,
    setCalculation: (fromDevice: Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        var selectedOption by remember { mutableStateOf(true) }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextSwitch(
                text = R.string.gir_counter_values_from,
                checked = selectedOption,
                onCheckedChange = { selectedOption = !selectedOption }
            )

            if (!selectedOption) {
                SetPar(
                    viewModel::onParChange,
                    viewModel.parValues,
                )

                val form by viewModel.greenForm.collectAsStateWithLifecycle()
                SetGreen(
                    form,
                    viewModel::onFormChange,
                    viewModel.greenFrontLat.value,
                    viewModel.greenFrontLong.value,
                    viewModel::onFrontLatChange,
                    viewModel::onFrontLongChange,
                    viewModel.greenMiddleLat.value,
                    viewModel.greenMiddleLong.value,
                    viewModel::onMiddleLatChange,
                    viewModel::onMiddleLongChange,
                    viewModel.greenBackLat.value,
                    viewModel.greenBackLong.value,
                    viewModel::onBackLatChange,
                    viewModel::onBackLongChange,
                    viewModel::onGreenChange,
                    viewModel.greenValues,
                )

                AddShot(
                    viewModel.shotLat.value,
                    viewModel::onShotLatChange,
                    viewModel.shotLong.value,
                    viewModel::onShotLongChange,
                    viewModel::onShotAdd,
                )
            }
        }

        TextFieldButton(
            text = stringResource(R.string.gir_counter_button_calculate),
            onClick = { setCalculation(selectedOption) },
            enabled = selectedOption || isCalculationEnabled,
            modifier = Modifier.padding(top = 16.dp),
            textModifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SetPar(
    onParChange: (Par) -> Unit,
    parValues: List<String>,
) {
    val title = stringResource(R.string.gir_counter_par_title)
    var parTitle by remember { mutableStateOf(title) }
    var parDropdown by remember { mutableStateOf(false) }
    BaseDropdownMenu(
        title = parTitle,
        data = parValues,
        onValueChange = {
            parTitle = it
            onParChange(
                when(it) {
                    parValues[0] -> {Par.PAR_3}
                    parValues[1] -> {Par.PAR_4}
                    else -> { Par.PAR_5 }
                }
            )
        },
        parDropdown,
        { parDropdown = !parDropdown }
    )
}

@Composable
fun SetGreen(
    form: GreenForm?,
    onFormChange: (GreenForm) -> Unit,
    frontLat: String,
    frontLong: String,
    onFrontLatChange: (String) -> Unit,
    onFrontLongChange: (String) -> Unit,
    middleLat: String,
    middleLong: String,
    onMiddleLatChange: (String) -> Unit,
    onMiddleLongChange: (String) -> Unit,
    backLat: String,
    backLong: String,
    onBackLatChange: (String) -> Unit,
    onBackLongChange: (String) -> Unit,
    onGreenChange: (Green) -> Unit,
    greenValues: List<String>,
) {
    var greenDialog by remember { mutableStateOf(false) }
    TextFieldButton(
        text = stringResource(R.string.gir_counter_green_title),
        onClick = { greenDialog = true }
    )

    if(greenDialog)
        Dialog(
            onDismissRequest = {
                greenDialog = false
            }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val title = stringResource(form?.string() ?: R.string.gir_counter_green_form)
                var greenTitle by remember { mutableStateOf(title) }
                var greenDropdown by remember { mutableStateOf(false) }
                BaseDropdownMenu(
                    title = greenTitle,
                    data = greenValues,
                    {
                        greenTitle = it
                        onFormChange(
                            when(it) {
                                greenValues[0] -> { GreenForm.ROUND }
                                else -> { GreenForm.SQUARE }
                            }
                        )
                    },
                    greenDropdown,
                    { greenDropdown = !greenDropdown }
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CoordinateTextField(
                        value = frontLat,
                        onValueChange = { onFrontLatChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_front_lat) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    CoordinateTextField(
                        value = frontLong,
                        onValueChange = { onFrontLongChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_front_long) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CoordinateTextField(
                        value = middleLat,
                        onValueChange = { onMiddleLatChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_middle_lat) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    CoordinateTextField(
                        value = middleLong,
                        onValueChange = {onMiddleLongChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_middle_long) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CoordinateTextField(
                        value = backLat,
                        onValueChange = { onBackLatChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_back_lat) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    CoordinateTextField(
                        value = backLong,
                        onValueChange = {onBackLongChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_back_long) },
                        modifier = Modifier.weight(1f)
                    )
                }

                TextFieldButton(
                    text = stringResource(R.string.gir_counter_green_title),
                    onClick = {
                        onGreenChange(
                            Green(
                                form = when(greenTitle) {
                                    greenValues[0] -> { GreenForm.ROUND }
                                    else -> { GreenForm.SQUARE }
                                },
                                front = GeoPoint(Latitude(frontLat.toDouble()), Longitude(frontLong.toDouble())),
                                middle = GeoPoint(Latitude(middleLat.toDouble()), Longitude(middleLong.toDouble())),
                                back = GeoPoint(Latitude(backLat.toDouble()), Longitude(backLong.toDouble()))
                            )
                        )
                        greenDialog = false
                    }
                )
            }
        }
}

@Composable
fun AddShot(
    shotLat: String,
    onShotLatChange: (String) -> Unit,
    shotLong: String,
    onShotLongChange: (String) -> Unit,
    onShotAdd: (GeoPoint) -> Unit
) {
    var shotDialog by remember { mutableStateOf(false) }
    TextFieldButton(
        text = stringResource(R.string.gir_counter_shot_title),
        onClick = { shotDialog = true }
    )

    if(shotDialog)
        Dialog(
            onDismissRequest = {
                shotDialog = false
            }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val title = stringResource(R.string.gir_counter_shot_title)
                var greenTitle by remember { mutableStateOf(title) }
                var greenDropdown by remember { mutableStateOf(false) }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CoordinateTextField(
                        value = shotLat,
                        onValueChange = { onShotLatChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_back_lat) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    CoordinateTextField(
                        value = shotLong,
                        onValueChange = {onShotLongChange(it) },
                        placeholder = { BodyLargeText(R.string.gir_counter_green_back_long) },
                        modifier = Modifier.weight(1f)
                    )
                }

                TextFieldButton(
                    text = stringResource(R.string.gir_counter_green_title),
                    onClick = {
                        onShotAdd(
                            GeoPoint(
                                lat = Latitude(shotLat.toDouble()),
                                lon = Longitude(shotLong.toDouble())
                            )
                        )
                        shotDialog = false
                    }
                )
            }
        }
}


@Composable
fun GIRCounterScreenCalculate(
    state: GIRCounterState.Calculation,
    calculateGIR: (state: GIRCounterState.Calculation) -> Unit
) {
    calculateGIR(state)
    FullScreenProgressIndicator()
}

@Composable
fun GIRCounterScreenResult(
    state: GIRCounterState.Result,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                LabelMediumText(R.string.gir_counter_result_title, modifier = Modifier.fillMaxWidth())
                BodyMediumText(
                    if(state.isGIR) R.string.gir_counter_result_body_susses
                    else R.string.gir_counter_result_body_failed,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

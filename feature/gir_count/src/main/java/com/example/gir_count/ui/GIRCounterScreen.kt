package com.example.gir_count.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.gir_count.domain.Par
import com.example.gir_count.presentation.GIRCounterState
import com.example.gir_count.presentation.GIRCounterViewModel
import com.example.ui.components.BodyMediumText
import com.example.ui.components.LabelMediumText
import com.example.ui.components.TextFieldButton
import com.example.ui.components.TextSwitch
import com.example.ui.components.TitleText
import com.example.ui.elements.BaseDropdownMenu
import com.example.ui.elements.FullScreenProgressIndicator
import org.koin.androidx.compose.koinViewModel

@Composable
fun GIRCounterScreen(

) {
    val viewModel: GIRCounterViewModel = koinViewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    TitleText(R.string.gir_counter_screen_name)

    Crossfade(targetState = state) { currentState ->
        when (currentState) {
            is GIRCounterState.Initial -> {
                GIRCounterScreenGetData(
                    viewModel::onParChange,
                    viewModel.parValues,
                    viewModel::onGreenFormChange,
                    viewModel.greenValues,
                    viewModel::onShotAdd,
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
    onParChange: (Par) -> Unit,
    parValues: List<String>,
    onGreenChange: (Green) -> Unit,
    greenValues: List<String>,
    onShotAdd: (GeoPoint) -> Unit,
    setCalculation: (Boolean) -> Unit
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
                    onParChange,
                    parValues
                )

                SetGreen(
                    onGreenChange,
                    greenValues,
                    //
                )
            }
        }

        TextFieldButton(
            text = stringResource(R.string.gir_counter_button_calculate),
            onClick = { setCalculation(selectedOption) },
            enabled = selectedOption,//
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
    var parDropdown by remember { mutableStateOf(false) }
    BaseDropdownMenu(
        title = stringResource(R.string.gir_counter_par_title),
        data = parValues,
        {
            onParChange(
                when(it) {
                    parValues[0] -> {Par.PAR_3}
                    parValues[1] -> {Par.PAR_4}
                    else -> { Par.PAR_5 }
                }
            )
            parDropdown = false
        },
        parDropdown,
        { parDropdown = false }
    )
}

@Composable
fun SetGreen(
    onGreenChange: (Green) -> Unit,
    greenValues: List<String>,
) {
    var greenDropdown by remember { mutableStateOf(false) }
    BaseDropdownMenu(
        title = stringResource(R.string.gir_counter_green_title),
        data = greenValues,
        {
//            onGreenChange(
//                when(it) {
//                    greenValues[0] -> {  }
//                    else -> {  }
//                }
//            )
            greenDropdown = false
        },
        greenDropdown,
        { greenDropdown = false }
    )
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

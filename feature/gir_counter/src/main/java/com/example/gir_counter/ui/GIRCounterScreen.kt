package com.example.gir_counter.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gir_counter.presentation.GIRCounterState
import com.example.gir_counter.presentation.GIRCounterViewModel
import com.example.ui.elements.FullScreenProgressIndicator
import com.example.ui.theme.GIRcounterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun GIRCounterScreen(

) {
    val viewModel: GIRCounterViewModel = koinViewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Crossfade(targetState = state) { currentState ->
        when (currentState) {
            is GIRCounterState.Initial -> {
                GIRCounterScreenGetData(
                    viewModel::setCalculation
                )
            }
            is GIRCounterState.Calculation -> {
                GIRCounterScreenCalculate { viewModel.calculate(currentState) }
            }
            is GIRCounterState.Result -> {
                GIRCounterScreenResult(
                    currentState,
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
    setCalculation: () -> Unit
) {

}

@Composable
fun GIRCounterScreenCalculate(
//    state: GIRCounterState.Calculation,
    calculateGIR: (state: GIRCounterState.Calculation) -> Unit
) {
    FullScreenProgressIndicator()
    run { calculateGIR }//(state)
}

@Composable
fun GIRCounterScreenResult(
    state: GIRCounterState.Result,
) {
    Dialog(
        onDismissRequest = {}
    ) {

    }
}

@Preview
@Composable
private fun Preview() {
    GIRcounterTheme {
        Scaffold(
            contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

            }
        }
    }
}
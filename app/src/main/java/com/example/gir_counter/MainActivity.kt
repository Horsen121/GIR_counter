package com.example.gir_counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gir_counter.ui.GIRCounterScreen
import com.example.ui.theme.GIRcounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GIRcounterTheme {
                val navController = rememberNavController()
                val currentRoute = rememberSaveable { mutableStateOf(NavigationOptions.GIR_COUNTER) }

                LaunchedEffect(key1 = Unit) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        val openedOption = NavigationOptions.entries.firstOrNull { destination.hasRoute(it.route) }

                        if (openedOption != null) {
                            currentRoute.value = openedOption
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = innerPadding.calculateTopPadding(),
                                start = 16.dp, end = 16.dp,
                                bottom = innerPadding.calculateBottomPadding()
                            )
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationOptions.GIR_COUNTER.route
                        ) {
                            composable<GIRCounterRoute> {
                                GIRCounterScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
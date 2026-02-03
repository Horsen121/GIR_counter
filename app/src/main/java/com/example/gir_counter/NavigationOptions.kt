package com.example.gir_counter

import com.example.gir_count.GIRCounterRoute
import kotlin.reflect.KClass

enum class NavigationOptions(val route: KClass<*>) {
    GIR_COUNTER(GIRCounterRoute::class)
}
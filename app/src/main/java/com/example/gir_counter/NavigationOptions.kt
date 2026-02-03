package com.example.gir_counter

import kotlin.reflect.KClass

enum class NavigationOptions(val route: KClass<*>) {
    GIR_COUNTER(GIRCounterRoute::class)
}
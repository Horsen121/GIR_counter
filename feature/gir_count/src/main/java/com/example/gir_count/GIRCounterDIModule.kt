package com.example.gir_count

import com.example.gir_count.presentation.GIRCounterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val GIRCounterModule = module {
    viewModelOf(::GIRCounterViewModel)
}
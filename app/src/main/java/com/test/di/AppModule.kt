package com.test.di

import dev.chrisbanes.haze.HazeState
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::HazeState)
}
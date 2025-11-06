package io.treblekit.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module

val appModules = arrayListOf<Module>()

fun KoinApplication.appleModules(): KoinApplication {
    return this@appleModules.apply {
        koin.loadModules(appModules)
    }
}
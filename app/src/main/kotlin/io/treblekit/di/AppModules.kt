package io.treblekit.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module

fun KoinApplication.appleModules(): KoinApplication {
    val appModules = arrayListOf<Module>()
    return this@appleModules.apply {
        koin.loadModules(appModules)
    }
}
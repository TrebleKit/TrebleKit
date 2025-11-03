package io.treblekit.di

import io.treblekit.common.ProxyHandler
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val appModules = arrayListOf<Module>()

fun KoinApplication.bridgeFlutter(proxy: ProxyHandler): KoinApplication {
    koin.loadModules(
        modules = listOf(
            module {
                single<ProxyHandler> {
                    return@single proxy
                }
            }
        ),
    )
    return this
}
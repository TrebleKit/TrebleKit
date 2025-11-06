package io.treblekit.di

import io.treblekit.common.ProxyHandler
import org.koin.core.KoinApplication
import org.koin.dsl.module

fun KoinApplication.bridgeFlutter(handler: ProxyHandler): KoinApplication {
    return this@bridgeFlutter.apply {
        module {
            single<ProxyHandler> {
                return@single handler
            }
        }.let { module ->
            koin.loadModules(modules = arrayListOf(module))
        }
    }
}
package io.treblekit.di

import io.treblekit.common.ProxyHandler
import io.treblekit.engine.EbKitPlugin
import io.treblekit.engine.EcosedPlugin
import org.koin.core.KoinApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PLUGIN_INSERT_NAMED: String = "ebkit_plugin"
const val PROXY_INSERT_NAMED: String = "bridge_proxy"

fun KoinApplication.bridgeFlutter(): KoinApplication {
    return module {
        EbKitPlugin().let { instance ->
            single<EcosedPlugin>(
                qualifier = named(
                    name = PLUGIN_INSERT_NAMED,
                ),
            ) {
                return@single instance
            }
            single<ProxyHandler>(
                qualifier = named(
                    name = PROXY_INSERT_NAMED,
                ),
            ) {
                return@single instance
            }
        }
    }.let { config ->
        return@let this@bridgeFlutter.apply {
            koin.loadModules(
                modules = arrayListOf(config),
            )
        }
    }
}
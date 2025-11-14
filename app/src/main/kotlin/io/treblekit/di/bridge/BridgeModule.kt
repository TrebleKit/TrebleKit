package io.treblekit.di.bridge

import io.treblekit.bridge.EbKit
import io.treblekit.common.EbConfig
import io.treblekit.common.ProxyHandler
import io.treblekit.plugin.TreblePlugin
import org.koin.core.qualifier.named
import org.koin.dsl.module

/** 桥接实例 */
private val instance = EbKit()

/** 桥接模块 */
val bridgeModule = module {
    // 插件单例
    single<TreblePlugin>(
        qualifier = named(name = EbConfig.DI_EBKIT_PLUGIN_NAMED),
    ) {
        return@single instance
    }
    // 调用代理程序单例
    single<ProxyHandler>(
        qualifier = named(name = EbConfig.DI_EBKIT_PROXY_NAMED),
    ) {
        return@single instance
    }
}
package io.treblekit.di.bridge

import io.treblekit.bridge.EbKit
import io.treblekit.common.ProxyHandler
import io.treblekit.engine.EcosedPlugin
import org.koin.core.qualifier.named
import org.koin.dsl.module

/** 插件 */
const val EBKIT_PLUGIN_NAMED: String = "ebkit_plugin"

/** 代理 */
const val EBKIT_PROXY_NAMED: String = "ebkit_proxy"

/** 桥接实例 */
private val instance = EbKit()

/** 桥接模块 */
val bridgeModule = module {
    // 插件单例
    single<EcosedPlugin>(
        qualifier = named(name = EBKIT_PLUGIN_NAMED),
    ) {
        return@single instance
    }
    // 调用代理程序单例
    single<ProxyHandler>(
        qualifier = named(name = EBKIT_PROXY_NAMED),
    ) {
        return@single instance
    }
}
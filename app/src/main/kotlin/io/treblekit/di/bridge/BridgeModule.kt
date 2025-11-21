package io.treblekit.di.bridge

import io.treblekit.bridge.EbKit
import io.treblekit.common.EbConfig
import io.treblekit.common.di.instance
import io.treblekit.common.proxy.MethodHandlerProxy
import io.treblekit.plugin.TrebleComponent
import org.koin.dsl.module

/** 桥接模块 */
val bridgeModule = module {
    // 桥接实例
    val ebkit = EbKit()
    // 插件单例
    instance<TrebleComponent>(
        name = EbConfig.DI_EBKIT_COMPONENT_NAMED,
        instance = ebkit
    )
    // 调用代理程序单例
    instance<MethodHandlerProxy>(
        name = EbConfig.DI_EBKIT_PROXY_NAMED,
        instance = ebkit
    )
}
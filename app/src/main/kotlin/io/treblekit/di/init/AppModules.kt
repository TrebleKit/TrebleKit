package io.treblekit.di.init

import io.treblekit.di.bridge.bridgeModule
import org.koin.core.KoinApplication
import org.koin.core.module.Module

/**
 * 加载依赖注入模块
 */
fun KoinApplication.applyModules(): KoinApplication {
    // 模块列表
    val appModules: ArrayList<Module> = arrayListOf(
        bridgeModule, // ebkit
    )
    // 加载模块
    modules(appModules)
    // 返回KoinApplication
    return this@applyModules
}
package io.treblekit.di.init

import io.treblekit.bridge.PlatformConnector
import org.koin.core.KoinApplication
import org.koin.core.module.Module

fun KoinApplication.applyModules() {
    // 模块列表
    val appModules = arrayListOf<Module>()
    // 平台连接器
    PlatformConnector.apply {
        insertConnector()
    }
    // 加载模块
    modules(appModules)
}
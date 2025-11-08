package io.treblekit.engine

import android.content.Context
import android.os.Bundle
import org.koin.core.component.KoinComponent

/**
 * 引擎包装器
 */
interface EngineWrapper : KoinComponent {

    /** 引擎初始化 */
    fun onCreateEngine(context: Context)

    /** 引擎销毁 */
    fun onDestroyEngine()

    /**
     * 执行方法
     * @param channel 插件通道
     * @param method 插件方法
     * @param bundle 传值
     * @return 执行插件方法返回值
     */
    fun <T> execMethodCall(
        channel: String,
        method: String,
        bundle: Bundle?,
    ): T?
}
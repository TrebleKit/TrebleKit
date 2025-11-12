package io.treblekit.plugin

import android.os.Bundle

/**
 * 插件方法执行器接口
 */
interface IExecutor {

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
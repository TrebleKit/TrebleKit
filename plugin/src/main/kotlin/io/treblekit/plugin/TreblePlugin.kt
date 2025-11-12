package io.treblekit.plugin

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle

/**
 * 基本插件
 */
abstract class TreblePlugin : ContextWrapper(null) {

    /** 插件通道 */
    private lateinit var mPluginChannel: PluginChannel

    /** 引擎 */
    private lateinit var mExecutor: IExecutor

    /** 是否调试模式 */
    private var mDebug: Boolean = false

    /**
     * 附加基本上下文
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    /**
     * 插件添加时执行
     */
    open fun onEcosedAdded(binding: PluginBinding) {
        // 初始化插件通道
        this@TreblePlugin.mPluginChannel = PluginChannel(
            binding = binding,
            channel = this@TreblePlugin.channel,
        )
        // 插件附加基本上下文
        this@TreblePlugin.attachBaseContext(
            base = this@TreblePlugin.mPluginChannel.getContext()
        )
        // 引擎
        this@TreblePlugin.mExecutor = this@TreblePlugin.mPluginChannel.getExecutor()
        // 获取是否调试模式
        this@TreblePlugin.mDebug = this@TreblePlugin.mPluginChannel.isDebug()
        // 设置调用
        this@TreblePlugin.mPluginChannel.setMethodCallHandler(
            handler = this@TreblePlugin
        )
    }

    /** 获取插件通道 */
    val getPluginChannel: PluginChannel
        get() = this@TreblePlugin.mPluginChannel

    /** 需要子类重写的插件标题 */
    abstract val title: String

    /** 需要子类重写的通道名称 */
    abstract val channel: String

    /** 需要子类重写的插件描述 */
    abstract val description: String

    /** 供子类使用的判断调试模式的接口 */
    protected val isDebug: Boolean
        get() = this@TreblePlugin.mDebug

    /**
     * 执行方法
     * @param channel 插件通道
     * @param method 插件方法
     * @param bundle 传值
     * @return 执行插件方法返回值
     */
    protected fun <T> execPluginMethod(
        channel: String,
        method: String,
        bundle: Bundle?,
    ): T? = this@TreblePlugin.mExecutor.execMethodCall(
        channel = channel,
        method = method,
        bundle = bundle,
    )

    /**
     * 插件调用方法
     */
    open fun onEcosedMethodCall(
        call: PluginMethodCall,
        result: PluginResult,
    ) = Unit
}
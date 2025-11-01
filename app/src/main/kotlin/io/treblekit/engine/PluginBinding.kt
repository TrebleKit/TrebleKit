package io.treblekit.engine

import android.content.Context


/**
 * 插件绑定器
 */
class PluginBinding(
    debug: Boolean,
    context: Context,
    engine: EngineWrapper,
) {

    /** 是否调试模式. */
    private val mDebug: Boolean = debug

    /** 应用程序全局上下文. */
    private val mContext: Context = context

    /** 引擎 */
    private val mEngine: EngineWrapper = engine

    /**
     * 是否调试模式.
     * @return Boolean.
     */
    fun isDebug(): Boolean = this@PluginBinding.mDebug

    /**
     * 获取上下文.
     * @return Context.
     */
    fun getContext(): Context = this@PluginBinding.mContext

    /**
     * 获取引擎
     * @return EngineWrapper.
     */
    fun getEngine(): EngineWrapper = this@PluginBinding.mEngine
}
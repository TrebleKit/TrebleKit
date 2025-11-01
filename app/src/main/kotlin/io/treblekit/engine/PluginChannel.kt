package io.treblekit.engine

import android.content.Context
import android.os.Bundle

/**
 * 插件通信通道
 */
class PluginChannel(
    binding: PluginBinding,
    channel: String,
) {

    /** 插件绑定器. */
    private var mBinding: PluginBinding = binding

    /** 插件通道. */
    private var mChannel: String = channel

    /** 方法调用处理接口. */
    private var mPlugin: EcosedPlugin? = null

    /** 方法名. */
    private var mMethod: String? = null

    /** 参数Bundle. */
    private var mBundle: Bundle? = null

    /** 返回结果. */
    private var mResult: Any? = null

    /**
     * 设置方法调用.
     * @param handler 执行方法时调用EcosedMethodCallHandler.
     */
    fun setMethodCallHandler(handler: EcosedPlugin) {
        this@PluginChannel.mPlugin = handler
    }

    /**
     * 获取上下文.
     * @return Context.
     */
    fun getContext(): Context = this@PluginChannel.mBinding.getContext()

    /**
     * 是否调试模式.
     * @return Boolean.
     */
    fun isDebug(): Boolean = this@PluginChannel.mBinding.isDebug()

    /**
     * 获取通道.
     * @return 通道名称.
     */
    fun getChannel(): String = this@PluginChannel.mChannel

    /**
     * 获取引擎.
     * @return 引擎.
     */
    fun getEngine(): EngineWrapper = this@PluginChannel.mBinding.getEngine()

    /**
     * 执行方法回调.
     * @param name 通道名称.
     * @param method 方法名称.
     * @return 方法执行后的返回值.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> execMethodCall(
        name: String,
        method: String?,
        bundle: Bundle?,
    ): T? {
        this@PluginChannel.mMethod = method
        this@PluginChannel.mBundle = bundle
        if (name == this@PluginChannel.mChannel) {
            this@PluginChannel.mPlugin?.onEcosedMethodCall(
                call = this@PluginChannel.call,
                result = this@PluginChannel.result,
            )
        }
        return this@PluginChannel.mResult as T?
    }

    /** 用于调用方法的接口. */
    private val call: EcosedMethodCall = object : EcosedMethodCall {

        /**
         * 要调用的方法名.
         */
        override val method: String?
            get() = this@PluginChannel.mMethod

        /**
         * 要传入的参数.
         */
        override val bundle: Bundle?
            get() = this@PluginChannel.mBundle
    }

    /** 方法调用结果回调. */
    private val result: EcosedResult = object : EcosedResult {

        /**
         * 处理成功结果.
         */
        override fun success(result: Any?) {
            this@PluginChannel.mResult = result
        }

        /**
         * 处理错误结果.
         */
        override fun error(
            errorCode: String,
            errorMessage: String?,
            errorDetails: Any?,
        ): Nothing = error(
            message = "错误代码:$errorCode\n错误消息:$errorMessage\n详细信息:$errorDetails"
        )

        /**
         * 处理对未实现方法的调用.
         */
        override fun notImplemented() {
            this@PluginChannel.mResult = null
        }
    }

}
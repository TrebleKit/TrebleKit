package io.treblekit.engine

import android.util.Log
import io.treblekit.common.ProxyHandler

class EbKitPlugin : EcosedPlugin(), ProxyHandler {

    override val title: String
        get() = "BridgePlugin"

    override val channel: String
        get() = "bridge_plugin"

    override val description: String
        get() = "BridgePlugin"

    override fun onProxyMethodCall(
        call: MethodCallProxy,
        result: ResultProxy,
    ) {
        try {
            // 执行代码并获取执行后的返回值
            execPluginMethod<Any>(
                channel = call.bundleProxy.getString(
                    "channel",
                    EcosedChannel.ENGINE_CHANNEL_NAME,
                ),
                method = call.methodProxy,
                bundle = call.bundleProxy,
            ).let { resultProxy ->
                // 判断是否为空并提交数据
                if (resultProxy.isNotNull) {
                    result.success(resultProxy = resultProxy)
                } else {
                    result.notImplemented()
                }
            }
        } catch (e: Exception) {
            // 抛出异常
            result.error(
                errorCodeProxy = TAG,
                errorMessageProxy = "BridgePlugin: onProxyMethodCall",
                errorDetailsProxy = Log.getStackTraceString(e),
            )
        }
    }

    override fun onEcosedAdded(binding: PluginBinding) {
        super.onEcosedAdded(binding)

    }

    override fun onEcosedMethodCall(call: EcosedMethodCall, result: EcosedResult) {
        super.onEcosedMethodCall(call, result)

    }

    private companion object {
        const val TAG: String = "BridgePlugin"
    }
}
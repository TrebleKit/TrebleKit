package io.treblekit.bridge

import android.util.Log
import io.treblekit.common.proxy.MethodHandlerProxy
import io.treblekit.engine.EcosedChannel
import io.treblekit.plugin.TreblePlugin
import io.treblekit.common.proxy.MethodCallProxy
import io.treblekit.common.proxy.MethodResultProxy
import io.treblekit.utils.isNotNull

class EbKit : TreblePlugin(), MethodHandlerProxy {

    override val title: String
        get() = "EbKit"

    override val channel: String
        get() = "ebkit"

    override val description: String
        get() = "EbKit"

    override fun onProxyMethodCall(
        call: MethodCallProxy,
        result: MethodResultProxy,
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
                    result.success(resultProxy = null)
                }
            }
        } catch (e: Exception) {
            // 抛出异常
            result.error(
                errorCodeProxy = TAG,
                errorMessageProxy = "onProxyMethodCall",
                errorDetailsProxy = Log.getStackTraceString(e),
            )
        }
    }

    private companion object {
        const val TAG: String = "BridgePlugin"
    }
}
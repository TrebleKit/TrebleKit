package io.treblekit.bridge

import android.os.Bundle
import android.util.Log
import com.kongzue.dialogx.dialogs.PopTip
import io.treblekit.common.proxy.MethodCallProxy
import io.treblekit.common.proxy.MethodHandlerProxy
import io.treblekit.common.proxy.MethodResultProxy
import io.treblekit.engine.EcosedChannel
import io.treblekit.plugin.PluginMethodCall
import io.treblekit.plugin.PluginResult
import io.treblekit.plugin.TrebleComponent

class EbKit : TrebleComponent(), MethodHandlerProxy {

    override val title: String
        get() = "EbKit"

    override val channel: String
        get() = EBKIT_CHANNEL

    override val description: String
        get() = "EbKit"

    override fun onTrebleMethodCall(
        call: PluginMethodCall,
        result: PluginResult,
    ) {
        super.onTrebleMethodCall(call, result)
        when (call.method) {
            "hello" -> {
                PopTip.show("hello")
                result.success(true)
            }

            "getPluginList" -> result.success(
                result = execPluginMethod(
                    channel = EcosedChannel.ENGINE_CHANNEL_NAME,
                    method = "getPluginList",
                    bundle = null
                )
            )
        }
    }

    override fun onProxyMethodCall(
        call: MethodCallProxy,
        result: MethodResultProxy,
    ) {
        try {
            // 执行代码并获取执行后的返回值
            result.success(
                resultProxy = execPluginMethod<Any>(
                    channel = call.argumentProxy<String>(
                        key = COMPONENT_CHANNEL_KEY,
                    ) ?: EBKIT_CHANNEL,
                    method = call.methodProxy,
                    bundle = Bundle(),
                ),
            )
        } catch (e: Exception) {
            // 抛出异常
            result.error(
                errorCodeProxy = TAG,
                errorMessageProxy = "onProxyMethodCall",
                errorDetailsProxy = Log.getStackTraceString(e),
            )
        }
    }

    /**
     * 伴生对象
     */
    private companion object {

        /** 日志打印标签 */
        const val TAG: String = "EbKit"

        /** Flutter调用时用来获取channel的键值 */
        const val COMPONENT_CHANNEL_KEY: String = "channel"

        /** EbKit的通道名称 */
        const val EBKIT_CHANNEL: String = "ebkit_platform"
    }
}
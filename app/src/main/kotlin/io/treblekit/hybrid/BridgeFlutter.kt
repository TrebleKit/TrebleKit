package io.treblekit.hybrid

import android.os.Bundle
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.treblekit.common.ProxyHandler
import io.treblekit.engine.MethodCallProxy
import io.treblekit.engine.ResultProxy
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BridgeFlutter : FlutterPlugin, MethodChannel.MethodCallHandler, KoinComponent {

    /** 方法通道 */
    private lateinit var mChannel: MethodChannel

    /** koin依赖注入 */
    private val mProxyHandler: ProxyHandler by inject<ProxyHandler>()

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mChannel = MethodChannel(binding.binaryMessenger, BRIDGE_FLUTTER_CHANNEL)
        mChannel.setMethodCallHandler(this@BridgeFlutter)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mChannel.setMethodCallHandler(null)
    }

    override fun onMethodCall(
        call: MethodCall,
        result: MethodChannel.Result,
    ) {
        mProxyHandler.onMethodCall(
            call = object : MethodCallProxy {

                override val methodProxy: String
                    get() = call.method

                override val bundleProxy: Bundle
                    get() = Bundle().let { bundle ->
                        bundle.putString("channel", call.argument<String>("channel"))
                        return@let bundle
                    }
            },
            result = object : ResultProxy {

                override fun success(
                    resultProxy: Any?,
                ) = result.success(
                    resultProxy
                )

                override fun error(
                    errorCodeProxy: String,
                    errorMessageProxy: String?,
                    errorDetailsProxy: Any?,
                ) = result.error(
                    errorCodeProxy,
                    errorMessageProxy,
                    errorDetailsProxy,
                )

                override fun notImplemented() = result.notImplemented()
            },
        )
    }

    private companion object {
        const val BRIDGE_FLUTTER_CHANNEL: String = "bridge_flutter"
    }
}
package io.treblekit.hybrid.plugin

import android.os.Bundle
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.treblekit.common.EbConfig
import io.treblekit.common.proxy.MethodCallProxy
import io.treblekit.common.proxy.MethodHandlerProxy
import io.treblekit.common.proxy.MethodResultProxy
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

/**
 * EbKit 桥接
 */
internal class EcosedBridge : FlutterPlugin, MethodChannel.MethodCallHandler, KoinComponent {

    /** 方法通道 */
    private lateinit var mChannel: MethodChannel

    /** 调用代理, 使用Koin依赖注入 */
    private val mHandler: MethodHandlerProxy by inject<MethodHandlerProxy>(
        qualifier = named(name = EbConfig.DI_EBKIT_PROXY_NAMED),
    )

    /**
     * 当插件附加到Flutter引擎时调用
     */
    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        // 初始化方法通道
        mChannel = MethodChannel(binding.binaryMessenger, ECOSED_BRIDGE_CHANNEL)
        // 设置方法调用处理
        mChannel.setMethodCallHandler(this@EcosedBridge)

    }

    /**
     * 当插件从Flutter引擎中分离时调用
     */
    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        // 清除方法调用处理
        mChannel.setMethodCallHandler(null)
    }

    /**
     * 当Flutter调用方法时调用
     */
    override fun onMethodCall(
        call: MethodCall,
        result: MethodChannel.Result,
    ) {
        try {
            mHandler.onProxyMethodCall(
                call = object : MethodCallProxy {

                    override val methodProxy: String
                        get() = call.method

                    override val bundleProxy: Bundle
                        get() = Bundle().let { bundle ->
                            bundle.putString(
                                "channel",
                                call.argument<String>("channel"),
                            )
                            return@let bundle
                        }
                },
                result = object : MethodResultProxy {

                    override fun success(
                        resultProxy: Any?,
                    ) = result.success(
                        resultProxy,
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
        } catch (e: Exception) {
            result.error("", "", e)
        }
    }

    /**
     * 私有伴生对象
     */
    private companion object {

        /** 桥接通道名称 */
        const val ECOSED_BRIDGE_CHANNEL: String = "ecosed_bridge"
    }
}
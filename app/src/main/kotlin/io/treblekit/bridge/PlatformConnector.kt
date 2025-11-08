package io.treblekit.bridge

import android.util.Log
import io.treblekit.common.ProxyHandler
import io.treblekit.engine.EcosedChannel
import io.treblekit.engine.EcosedMethodCall
import io.treblekit.engine.EcosedPlugin
import io.treblekit.engine.EcosedResult
import io.treblekit.engine.MethodCallProxy
import io.treblekit.engine.ResultProxy
import io.treblekit.engine.isNotNull
import org.koin.core.KoinApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

class PlatformConnector private constructor() {

    /**
     * 定义连接器实现的基础
     */
    private abstract class AbstractConnector : EcosedPlugin(), ProxyHandler

    /**
     * 定义平台连接器构建接口
     */
    private interface IBuilder {

        /**
         * 插入连接器
         */
        fun KoinApplication.insertConnector(): KoinApplication
    }

    /** 连接器实现 */
    private val mConnector: AbstractConnector = object : AbstractConnector() {

        override val title: String
            get() = "EbKit"

        override val channel: String
            get() = "ebkit_connector"

        override val description: String
            get() = "平台连接器"

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

        override fun onEcosedMethodCall(call: EcosedMethodCall, result: EcosedResult) {
            super.onEcosedMethodCall(call, result)
            when {

            }
        }
    }

    /** 平台连接器构建实现 */
    private val mBuilder = object : IBuilder {

        override fun KoinApplication.insertConnector(): KoinApplication {
            return module {
                mConnector.let { instance ->
                    single<EcosedPlugin>(
                        qualifier = named(
                            name = EBKIT_PLUGIN_NAMED,
                        ),
                    ) {
                        return@single instance
                    }
                    single<ProxyHandler>(
                        qualifier = named(
                            name = EBKIT_PROXY_NAMED,
                        ),
                    ) {
                        return@single instance
                    }
                }
            }.let { config ->
                return@let this@insertConnector.apply {
                    koin.loadModules(
                        modules = arrayListOf(config),
                    )
                }
            }
        }
    }

    /**
     * 伴生对象
     */
    companion object : IBuilder by PlatformConnector().mBuilder {
        private const val TAG: String = "BridgePlugin"

        const val EBKIT_PLUGIN_NAMED: String = "ebkit_plugin"
        const val EBKIT_PROXY_NAMED: String = "bridge_proxy"
    }
}
package io.treblekit.engine

import android.content.Context
import android.os.Bundle
import android.util.Log
import io.treblekit.BuildConfig
import io.treblekit.common.EbConfig
import io.treblekit.utils.injectInstance
import io.treblekit.engine.Engine.Companion.TAG
import io.treblekit.plugin.IExecutor
import io.treblekit.plugin.PluginBinding
import io.treblekit.plugin.PluginMethodCall
import io.treblekit.plugin.PluginResult
import io.treblekit.plugin.TrebleComponent
import io.treblekit.utils.isNotNull
import io.treblekit.utils.isNull
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class TrebleEngine : TrebleComponent(), KoinComponent, IExecutor, IEngine {

    /** 供引擎使用的基本调试布尔值 */
    private val mBaseDebug: Boolean = BuildConfig.DEBUG

    /** 通过依赖注入获取到应用程序全局上下文 */
    private val mContext: Context by inject<Context>()

    /** 通过依赖注入获取到连接器插件 */
    private val mConnector: TrebleComponent by injectInstance(
        name = EbConfig.DI_EBKIT_COMPONENT_NAMED,
    )

    /** 插件绑定器. */
    private var mBinding: PluginBinding? = null

    /** 插件列表. */
    private var mPluginList: ArrayList<TrebleComponent>? = null

    private var mJSONList: ArrayList<String>? = null

    /** 插件标题 */
    override val title: String
        get() = "EcosedEngine"

    /** 插件通道 */
    override val channel: String
        get() = EcosedChannel.ENGINE_CHANNEL_NAME

    /** 插件描述 */
    override val description: String
        get() = "Ecosed Engine"

    /**
     * 引擎初始化时执行
     */
    override fun onComponentAdded(binding: PluginBinding): Unit = run {
        super.onComponentAdded(binding)
        // 设置来自插件的全局调试布尔值
//            mFullDebug = this@run.isDebug
    }

    override fun onTrebleMethodCall(call: PluginMethodCall, result: PluginResult) {
        super.onTrebleMethodCall(call, result)
        when (call.method) {
            "getPluginList" -> result.success(result = mJSONList)
            else -> result.notImplemented()
        }
    }

    /**
     * 引擎初始化.
     * @param context 上下文 - 此上下文来自FlutterPlugin的ApplicationContext
     */
    override fun onCreateEngine() {
        if (mPluginList.isNull or mJSONList.isNull or mBinding.isNull) {
            // 初始化插件列表.
            mPluginList = arrayListOf()
            // 初始化组件数据列表
            mJSONList = arrayListOf()
            // 初始化插件绑定
            mBinding = PluginBinding(
                debug = mBaseDebug,
                context = mContext,
                executor = this@TrebleEngine,
            )
            // 添加所有插件.
            arrayListOf(this@TrebleEngine, mConnector).let { plugins ->
                TreblePluginRegistrant.registerWith(plugins = plugins)
                return@let plugins
            }.forEach { plugin ->
                plugin.apply {
                    try {
                        this@apply.onComponentAdded(binding = mBinding!!)
                        if (mBaseDebug) {
                            Log.d(
                                TAG,
                                "插件${this@apply.javaClass.name}已加载",
                            )
                        }
                    } catch (exception: Exception) {
                        if (mBaseDebug) {
                            Log.e(
                                TAG,
                                "插件${this@apply.javaClass.name}添加失败!",
                                exception,
                            )
                        }
                    }
                }.run {
                    mPluginList?.add(
                        element = this@run,
                    )
                    mJSONList?.add(
                        element = JSONObject().let { json ->
                            json.put("channel", this@run.channel)
                            json.put("title", this@run.title)
                            json.put("description", this@run.description)
                            return@let json.toString()
                        },
                    )
                    if (mBaseDebug) {
                        Log.d(
                            TAG,
                            "插件${this@run.javaClass.name}已添加到插件列表",
                        )
                    }
                }
            }
        } else {
            if (mBaseDebug) {
                Log.e(
                    TAG, "请勿重复执行onCreateEngine!"
                )
            }
        }
    }

    /**
     * 销毁引擎释放资源.
     */
    override fun onDestroyEngine() {
        if (mPluginList.isNotNull or mJSONList.isNotNull or mBinding.isNotNull) {
            // 清空插件列表
            mPluginList = null
        } else if (mBaseDebug) {
            Log.e(TAG, "请勿重复执行onDestroyEngine!")
        }
    }

    /**
     * 调用插件代码的方法.
     * @param channel 要调用的插件的通道.
     * @param method 要调用的插件中的方法.
     * @param bundle 通过Bundle传递参数.
     * @return 返回方法执行后的返回值,类型为Any?.
     */
    override fun <T> execMethodCall(
        channel: String,
        method: String,
        bundle: Bundle?,
    ): T? {
        var result: T? = null
        try {
            mPluginList?.forEach { plugin ->
                plugin.getPluginChannel.let { pluginChannel ->
                    if (pluginChannel.getChannel() == channel) {
                        result = pluginChannel.execMethodCall(
                            name = channel,
                            method = method,
                            bundle = bundle,
                        )
                        if (mBaseDebug) Log.d(
                            TAG,
                            "插件代码调用成功!\n通道名称:${channel}.\n方法名称:${method}.\n返回结果:${result}.",
                        )
                    }
                }
            }
        } catch (exception: Exception) {
            if (mBaseDebug) {
                Log.e(
                    TAG,
                    "插件代码调用失败!",
                    exception,
                )
            }
        }
        return result
    }

    companion object {

    }
}
package io.treblekit.hybrid.register

import android.util.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.treblekit.hybrid.plugin.AndroidToFlutter
import io.treblekit.hybrid.plugin.EcosedBridge
import io.treblekit.hybrid.plugin.PlatformResources

/**
 * 自定义Flutter插件注册器
 */
internal object CustomPluginRegistrant {

    /** 注册器日志标签 */
    private const val TAG: String = "CustomPluginRegistrant"

    /** 插件列表 */
    private val mPlugins: ArrayList<FlutterPlugin> = arrayListOf(
        PlatformResources(), // 平台资源
        EcosedBridge(), // EbKit 桥接
        AndroidToFlutter(), // Android 到 Flutter 通信
    )

    /**
     * 注册插件
     *
     * @param engine Flutter引擎
     */
    internal fun registerWith(engine: FlutterEngine) {
        mPlugins.forEach { plugin ->
            try {
                engine.plugins.add(plugin)
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Error registering plugin ${plugin.javaClass.name}",
                    e,
                )
            }
        }
    }
}
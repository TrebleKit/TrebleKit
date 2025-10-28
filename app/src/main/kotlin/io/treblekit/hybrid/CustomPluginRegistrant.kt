package io.treblekit.hybrid

import android.util.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin

object CustomPluginRegistrant {

    private const val TAG: String = "CustomPluginRegistrant"

    private val mPlugins: ArrayList<FlutterPlugin> = arrayListOf(
        PlatformResources(),
        AndroidToFlutter(),
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
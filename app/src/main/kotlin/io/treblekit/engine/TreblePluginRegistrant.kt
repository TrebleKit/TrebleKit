package io.treblekit.engine

import android.util.Log
import io.treblekit.plugin.TreblePlugin

internal object TreblePluginRegistrant {

    /** 注册器日志标签 */
    private const val TAG: String = "TreblePluginRegistrant"

    /** 插件列表 */
    private val mPlugins: ArrayList<TreblePlugin> by lazy {
        return@lazy arrayListOf()
    }

    /**
     * 注册插件
     */
    internal fun registerWith(plugins: ArrayList<TreblePlugin>) {
        mPlugins.forEach { plugin ->
            try {
                plugins.add(plugin)
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
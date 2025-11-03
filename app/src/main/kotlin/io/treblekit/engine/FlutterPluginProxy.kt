package io.treblekit.engine

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import io.treblekit.common.ProxyHandler

/**
 * Flutter插件代理
 */
interface FlutterPluginProxy : ProxyHandler {

    /** 注册Activity引用 */
    fun onCreateActivity(activity: Activity)

    /** 注销Activity引用 */
    fun onDestroyActivity()

    /** 注册生命周期监听器 */
    fun onCreateLifecycle(lifecycle: Lifecycle)

    /** 注销生命周期监听器释放资源避免内存泄露 */
    fun onDestroyLifecycle()

    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ): Boolean

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ): Boolean

    /** 引擎初始化 */
    fun onCreateEngine(context: Context)

    /** 引擎销毁 */
    fun onDestroyEngine()
}
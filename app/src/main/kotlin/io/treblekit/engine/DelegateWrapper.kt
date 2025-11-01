package io.treblekit.engine

import android.content.Intent
import android.os.IBinder

/**
 * 服务插件包装器
 */
interface DelegateWrapper : ConnectWrapper, LifecycleWrapper {

    /**
     * 获取Binder
     */
    fun getBinder(intent: Intent): IBinder

    /**
     * 附加代理基本上下文
     */
    fun attachDelegateBaseContext()
}
package io.treblekit.app.hybrid

import io.treblekit.app.base.BaseApplication

abstract class HybridApplication : BaseApplication() {

    /**
     * 初始化
     */
    override fun onCreate() {
        super.onCreate()
        // 初始化Flutter引擎
        loadFlutter()
    }
}
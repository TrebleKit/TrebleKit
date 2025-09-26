package io.treblekit.app.hybrid

import io.treblekit.app.base.BaseApplication

abstract class HybridApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        loadFlutter().apply {
            plugins.add(PlatformImagePlugin())
            plugins.add(AndroidToFlutter())
        }
    }
}
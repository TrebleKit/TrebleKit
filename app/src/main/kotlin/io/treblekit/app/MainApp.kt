package io.treblekit.app

import com.google.android.material.color.DynamicColors
import io.treblekit.app.hybrid.HybridApplication

class MainApp : HybridApplication() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this@MainApp)
    }
}
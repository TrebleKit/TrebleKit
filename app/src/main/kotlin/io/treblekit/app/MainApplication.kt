package io.treblekit.app

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.wyq0918dev.flutter_mixed.FlutterMixedPlugin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FlutterMixedPlugin.initFlutter(this@MainApplication)
        DynamicColors.applyToActivitiesIfAvailable(this@MainApplication)
    }
}
package io.treblekit.app

import android.app.Application
import android.content.Context
import com.google.android.material.color.DynamicColors
import io.treblekit.app.hybrid.loadFlutterEngine
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HiddenApiBypass.addHiddenApiExemptions("L")
    }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this@MainApp)
        loadFlutterEngine() // 初始化Flutter引擎
    }
}
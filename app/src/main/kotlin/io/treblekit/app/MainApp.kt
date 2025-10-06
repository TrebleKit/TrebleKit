package io.treblekit.app

import android.app.Application
import android.content.Context
import android.os.Build
import com.google.android.material.color.DynamicColors
import io.treblekit.app.hybrid.loadEngine
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HiddenApiBypass.addHiddenApiExemptions("L")
    }

    override fun onCreate() {
        super.onCreate()
        loadEngine()
        DynamicColors.applyToActivitiesIfAvailable(this@MainApp)
    }
}
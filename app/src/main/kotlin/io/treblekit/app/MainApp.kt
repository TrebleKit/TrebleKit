package io.treblekit.app

import android.content.Context
import android.os.Build
import com.google.android.material.color.DynamicColors
import io.treblekit.app.hybrid.HybridApplication
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainApp : HybridApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.addHiddenApiExemptions("L")
        }
    }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this@MainApp)
    }
}
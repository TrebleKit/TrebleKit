package io.treblekit.app

import android.content.Context
import com.google.android.material.color.DynamicColors
import com.kongzue.baseframework.BaseApp
import io.treblekit.app.hybrid.loadFlutterEngine
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainApp : BaseApp<MainApp>() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HiddenApiBypass.addHiddenApiExemptions("L")
    }

    override fun init() {
        DynamicColors.applyToActivitiesIfAvailable(this@MainApp)
        loadFlutterEngine() // 初始化Flutter引擎
    }
}
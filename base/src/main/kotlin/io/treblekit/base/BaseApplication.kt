package io.treblekit.base

import android.content.Context
import com.kongzue.baseframework.BaseApp
import com.kongzue.baseframework.BaseFrameworkSettings
import com.kongzue.baseframework.interfaces.OnBugReportListener
import com.kongzue.baseframework.util.AppManager
import java.io.File

abstract class BaseApplication : BaseApp<BaseApplication>() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        onCreateContext()
    }

    private val mCrash: OnBugReportListener = object : OnBugReportListener() {

        override fun onCrash(
            e: Exception?,
            crashLogFile: File
        ): Boolean {
            val activity = AppManager.getInstance().activeActivity
            if (activity == null || !activity.isActive) return false
            onShowCrashDialog()
            return false
        }
    }

    private fun initBaseFramework() {
        BaseFrameworkSettings.DEBUGMODE = BuildConfig.DEBUG // 调试配置
        BaseFrameworkSettings.BETA_PLAN = true
        setOnCrashListener(mCrash)
    }

    override fun init() {
        initBaseFramework()
        onInitDependence()
        onCreateEngine()
        onCreateFlutter()
    }

    override fun initSDKs() {
        super.initSDKs()
        onInitAsync()
    }

    abstract fun onCreateContext()
    abstract fun onInitDependence()
    abstract fun onInitAsync()

    abstract fun onCreateEngine()
    abstract fun onCreateFlutter()

    abstract fun onShowCrashDialog()
}
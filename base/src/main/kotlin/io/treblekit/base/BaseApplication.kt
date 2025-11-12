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
        onInitFirst()
    }

    private val mCrash: OnBugReportListener = object : OnBugReportListener() {
        override fun onCrash(e: Exception?, crashLogFile: File): Boolean {
            if (AppManager.getInstance().activeActivity == null || !AppManager.getInstance()
                    .activeActivity.isActive
            ) {
                return false
            }

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
        onInitEngine()
        onInitHybrid()
    }

    override fun initSDKs() {
        super.initSDKs()
        onInitAsync()
    }

    abstract fun onInitFirst()
    abstract fun onInitDependence()
    abstract fun onInitAsync()

    abstract fun onInitEngine()
    abstract fun onInitHybrid()

    abstract fun onShowCrashDialog()
}
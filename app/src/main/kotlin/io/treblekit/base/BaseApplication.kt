package io.treblekit.base

import android.content.Context
import com.kongzue.baseframework.BaseApp
import com.kongzue.baseframework.BaseFrameworkSettings
import com.kongzue.baseframework.interfaces.OnBugReportListener
import com.kongzue.baseframework.util.AppManager
import com.kongzue.dialogx.dialogs.MessageDialog
import io.treblekit.BuildConfig
import java.io.File

abstract class BaseApplication : BaseApp<BaseApplication>() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        onInitHiddenApi()
    }

    private val mCrash: OnBugReportListener = object : OnBugReportListener() {
        override fun onCrash(e: Exception?, crashLogFile: File): Boolean {
            if (AppManager.getInstance().getActiveActivity() == null || !AppManager.getInstance()
                    .getActiveActivity().isActive
            ) {
                return false
            }
            MessageDialog.show(
                "Ops！发生了一次崩溃！",
                "您是否愿意帮助我们改进程序以修复此Bug？",
                "愿意",
            )
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
    }

    override fun initSDKs() {
        super.initSDKs()
        onInitAsync()
    }

    abstract fun onInitHiddenApi()
    abstract fun onInitDependence()
    abstract fun onInitAsync()
}
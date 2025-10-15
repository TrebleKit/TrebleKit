package io.treblekit.app

import android.content.Context
import com.google.android.material.color.DynamicColors
import com.kongzue.baseframework.BaseApp
import com.kongzue.baseframework.BaseFrameworkSettings
import com.kongzue.baseframework.interfaces.OnBugReportListener
import com.kongzue.baseframework.util.AppManager
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogxmaterialyou.style.MaterialYouStyle
import io.treblekit.app.di.appModules
import io.treblekit.app.hybrid.loadFlutterEngine
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.lsposed.hiddenapibypass.HiddenApiBypass
import rikka.sui.Sui
import java.io.File

class MainApplication : BaseApp<MainApplication>() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HiddenApiBypass.addHiddenApiExemptions("L")
    }

    override fun init() {
       initBaseFramework()

        DynamicColors.applyToActivitiesIfAvailable(this@MainApplication)
        loadFlutterEngine() // 初始化Flutter引擎
        Sui.init(BuildConfig.APPLICATION_ID)


        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModules)
            modules(module { single { this@MainApplication } })
        }
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

    override fun initSDKs() {
        super.initSDKs()
        initDialogX() // 初始化DialogX
    }

    private fun initBaseFramework() {
        BaseFrameworkSettings.DEBUGMODE = BuildConfig.DEBUG // 调试配置
        BaseFrameworkSettings.BETA_PLAN = true
        setOnCrashListener(mCrash)
    }

    private fun initDialogX() {
        DialogX.init(this) // 初始化
        DialogX.implIMPLMode = DialogX.IMPL_MODE.VIEW // 使用View实现
        DialogX.useHaptic = true // 启用震动反馈
        DialogX.globalStyle = MaterialYouStyle() // Material3样式
        DialogX.globalTheme = DialogX.THEME.AUTO // 自动切换深浅色主题
        DialogX.onlyOnePopTip = false // 可以显示多个PopTip
        DialogX.onlyOnePopNotification = false // 可以显示多个通知
        DialogX.DEBUGMODE = BuildConfig.DEBUG // 调试配置
    }
}
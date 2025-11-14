package io.treblekit.app

import com.google.android.material.color.DynamicColors
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogxmaterialyou.style.MaterialYouStyle
import io.treblekit.BuildConfig
import io.treblekit.di.init.applyModules
import io.treblekit.engine.loadTrebleEngine
import io.treblekit.hybrid.base.HybridApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.lsposed.hiddenapibypass.HiddenApiBypass
import rikka.sui.Sui

class MainApplication : HybridApplication() {

    override fun onInitFirst() {
        HiddenApiBypass.addHiddenApiExemptions("L")
    }

    override fun onInitDependence() {
        // 初始化Sui
        Sui.init(BuildConfig.APPLICATION_ID)
        // 应用全局动态颜色
        DynamicColors.applyToActivitiesIfAvailable(this@MainApplication)
        // 初始化Koin依赖注入
        startKoin {
            androidLogger()
            androidContext(androidContext = this@MainApplication)
            applyModules()
        }
    }

    override fun onInitAsync() {
        // 初始化DialogX
        DialogX.init(this) // 初始化
        DialogX.implIMPLMode = DialogX.IMPL_MODE.VIEW // 使用View实现
        DialogX.useHaptic = true // 启用震动反馈
        DialogX.globalStyle = MaterialYouStyle() // Material3样式
        DialogX.globalTheme = DialogX.THEME.AUTO // 自动切换深浅色主题
        DialogX.onlyOnePopTip = false // 可以显示多个PopTip
        DialogX.onlyOnePopNotification = false // 可以显示多个通知
        DialogX.DEBUGMODE = BuildConfig.DEBUG // 调试配置
    }

    override fun onInitEngine() {
        // 初始化Treble引擎
        loadTrebleEngine()
    }

    override fun onShowCrashDialog() {
        MessageDialog.show(
            "Ops！发生了一次崩溃！",
            "您是否愿意帮助我们改进程序以修复此Bug？",
            "愿意",
        )
    }
}
package io.treblekit.app

import com.google.android.material.color.DynamicColors
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogxmaterialyou.style.MaterialYouStyle
import io.treblekit.BuildConfig
import io.treblekit.base.BaseApplication
import io.treblekit.di.appleModules
import io.treblekit.di.bridgeFlutter
import io.treblekit.engine.loadTrebleEngine
import io.treblekit.hybrid.loadFlutterEngine
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.lsposed.hiddenapibypass.HiddenApiBypass
import rikka.sui.Sui

class MainApplication : BaseApplication() {

    override fun onInitHiddenApi() {
        HiddenApiBypass.addHiddenApiExemptions("L")
    }

    override fun onInitDependence() {
        // 应用全局动态颜色
        DynamicColors.applyToActivitiesIfAvailable(this@MainApplication)
        // 初始化Koin依赖注入
        startKoin {
            androidLogger()
            androidContext(androidContext = this@MainApplication)
            bridgeFlutter()
            appleModules()
        }
        // 初始化Treble引擎
        loadTrebleEngine()
        // 初始化Flutter引擎
        loadFlutterEngine()
        // 初始化Sui
        Sui.init(BuildConfig.APPLICATION_ID)
    }

    override fun onInitAsync() {
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
package io.treblekit.app

import com.google.android.material.color.DynamicColors
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogxmaterialyou.style.MaterialYouStyle
import io.treblekit.BuildConfig
import io.treblekit.base.BaseApplication
import io.treblekit.common.ProxyHandler
import io.treblekit.di.appModules
import io.treblekit.di.bridgeFlutter
import io.treblekit.engine.EcosedPlugin
import io.treblekit.engine.MethodCallProxy
import io.treblekit.engine.ResultProxy
import io.treblekit.hybrid.loadFlutterEngine
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.lsposed.hiddenapibypass.HiddenApiBypass
import rikka.sui.Sui

class MainApplication : BaseApplication() {

    private val mProxy: ProxyHandler = object : EcosedPlugin(), ProxyHandler {

        override fun onMethodCall(
            call: MethodCallProxy,
            result: ResultProxy,
        ) {
//            try {
//                result.success(
//                    resultProxy = execPluginMethod(
//                        channel = call.bundleProxy.getString("channel") ?: error(message = ""),
//                        method = call.methodProxy,
//                        bundle = call.bundleProxy,
//                    ),
//                )
//            } catch (e: Exception) {
//                result.error(
//                    errorCodeProxy = "",
//                    errorMessageProxy = "",
//                    errorDetailsProxy = e,
//                )
//            }

            when (call.methodProxy) {
                "hello" -> PopTip.show("hello")
                else -> result.notImplemented()
            }
        }

        override val title: String
            get() = TODO("Not yet implemented")
        override val channel: String
            get() = TODO("Not yet implemented")
        override val author: String
            get() = TODO("Not yet implemented")
        override val description: String
            get() = TODO("Not yet implemented")
    }

    override fun onInitHiddenApi() {
        HiddenApiBypass.addHiddenApiExemptions("L")
    }

    override fun onInitDependence() {
        // 应用全局动态颜色
        DynamicColors.applyToActivitiesIfAvailable(this@MainApplication)
        startKoin {
            androidLogger()
            androidContext(androidContext = this@MainApplication)
            bridgeFlutter(proxy = mProxy)
            modules(appModules)
        }
        // 初始化Flutter引擎
        loadFlutterEngine()
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
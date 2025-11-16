package io.treblekit.base

import android.graphics.Color
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.kongzue.baseframework.BaseActivity
import com.kongzue.baseframework.util.JumpParameter

abstract class BaseActivity : BaseActivity() {

    override fun resetContentView(): View? {
        return onCreateView()
    }

    override fun initViews() {
        edgeToEdge()
        initFlutter()
    }

    override fun initDatas(parameter: JumpParameter?) = Unit

    override fun setEvents() = Unit

    /**
     * 启用边倒边
     */
    private fun edgeToEdge() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.isNavigationBarContrastEnforced = false
    }

    abstract fun onCreateView(): View

    /**
     * 抽象接口定义
     * 初始化FlutterFragment
     */
    abstract fun initFlutter()
}
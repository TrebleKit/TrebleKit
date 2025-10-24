package io.treblekit.base

import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.kongzue.baseframework.BaseActivity
import com.kongzue.baseframework.util.JumpParameter

abstract class BaseActivity : BaseActivity() {

    override fun initViews() {
        edgeToEdge()
        initFlutter()

//        throw RuntimeException("err")
    }

    override fun initDatas(p0: JumpParameter?) {

    }

    override fun setEvents() {

    }

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

    /**
     * 抽象接口定义
     * 初始化FlutterFragment
     */
    abstract fun initFlutter()
}
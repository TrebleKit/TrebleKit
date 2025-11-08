package io.treblekit.base

import android.graphics.Color
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.kongzue.baseframework.BaseActivity
import com.kongzue.baseframework.util.JumpParameter

abstract class BaseActivity : BaseActivity() {

    private var mView: View? = null

    override fun resetContentView(): View? {
        mView = onCreateView()
        return mView
    }

    override fun initViews() {
        edgeToEdge()
        initFlutter()
        onViewCreated(view = mView)
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

    abstract fun onCreateView(): View

    abstract fun onViewCreated(view: View?)

    /**
     * 抽象接口定义
     * 初始化FlutterFragment
     */
    abstract fun initFlutter()
}
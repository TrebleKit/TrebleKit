package io.treblekit.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.AbstractComposeView
import io.treblekit.ui.activity.ActivityMain
import io.treblekit.ui.factory.IViewFactory
import io.treblekit.ui.factory.LocalViewFactory
import io.treblekit.ui.theme.TrebleKitTheme

/**
 * 支持混合开发的ComposeView
 */
internal class HybridComposeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(
    context = context,
    attrs = attrs,
    defStyleAttr = defStyleAttr,
) {

    /**
     * 初始化
     */
    init {
        // 必须加此代码否则不符合预期
        // 支持混合开发
        consumeWindowInsets = false
        if (isAttachedToWindow) {
            createComposition()
        }
    }

    private val mContext: Context = context

    private val mViewFactory: IViewFactory = object : IViewFactory {

        override val overlayView: View by lazy {
            return@lazy OverlayView(context = mContext)
        }

        override val effectView: View by lazy {
            return@lazy StreamerEffectView(context = mContext)
        }

        override val wrapperView: View by lazy {
            return@lazy FlutterWrapperView(context = mContext)
        }
    }

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = true
        private set

    /**
     * 绘制Compose布局
     */
    @Composable
    override fun Content() {
        CompositionLocalProvider(
            value = LocalViewFactory provides mViewFactory,
        ) {
            TrebleKitTheme {
                ActivityMain()
            }
        }
    }

    /**
     * 无障碍
     */
    override fun getAccessibilityClassName(): CharSequence {
        return javaClass.name
    }
}
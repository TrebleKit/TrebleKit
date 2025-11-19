package io.treblekit.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import io.treblekit.ui.activity.ActivityMain
import io.treblekit.ui.factory.ViewFactoryLocalProvider
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
        // 为此视图进行初步构图
        if (isAttachedToWindow) {
            createComposition()
        }
    }

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = true
        private set

    /**
     * 绘制Compose布局
     */
    @Composable
    override fun Content() {
        ViewFactoryLocalProvider {
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
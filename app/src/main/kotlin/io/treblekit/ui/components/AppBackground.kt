package io.treblekit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.appBackground

/**
 * AppBackground 是应用的背景, 包括背景色,和流光背景效果
 *
 * @param modifier 修饰符
 * @param content 子视图内容
 */
@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(LayerBackdrop) -> Unit = {},
) {
    val backdrop: LayerBackdrop = rememberLayerBackdrop() // Backdrop
    val inspection: Boolean = LocalInspectionMode.current // 检查模式
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = appBackground) // 背景色
                .layerBackdrop(backdrop = backdrop), // Backdrop
        ) {
            // 非检查模式下, 显示流光背景效果
            if (!inspection) ViewFactory(
                modifier = Modifier
                    .fillMaxSize()
                    .layout { measurable, constraints ->
                        val placeable: Placeable = measurable.measure(constraints)
                        layout(
                            width = placeable.width,
                            height = placeable.height,
                        ) {
                            // 偏移到屏幕高度7分之2处
                            placeable.placeRelative(
                                x = 0,
                                y = constraints.maxHeight / 7 * 2,
                            )
                        }
                    },
            ) {
                getEffectView // 流光背景效果
            }
        }
        // 前景内容
        content.invoke(this@Box, backdrop)
    }
}

/**
 * AppBackground 的预览
 */
@Preview
@Composable
private fun AppBackgroundPreview() {
    TrebleKitTheme {
        AppBackground()
    }
}
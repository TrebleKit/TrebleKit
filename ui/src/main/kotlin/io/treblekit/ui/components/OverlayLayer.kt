package io.treblekit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import io.treblekit.ui.factory.ViewFactory
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.capsuleEdgePadding
import io.treblekit.ui.theme.capsuleHeight

/**
 * 应用叠加层, 用于显示角标和胶囊按钮, 在检查模式下显示由Compose实现的胶囊按钮.
 *
 * @param modifier 修饰符
 * @param backdrop Backdrop
 * @param content 子视图内容
 */
@Composable
fun OverlayLayer(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
    content: @Composable BoxScope.() -> Unit = {},
) {
    val inspection: Boolean = LocalInspectionMode.current // 检查模式
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        content.invoke(this@Box) // 子视图内容
        if (!inspection) {
            // 非检查模式下, 显示叠加层视图
            ViewFactory(
                modifier = Modifier.fillMaxSize()
            ) {
                overlayView // 叠加层视图
            }
        } else {
            // 检查模式下, 显示胶囊按钮
            CapsuleButton(
                modifier = Modifier
                    .align(Alignment.TopEnd) // 右上角
                    .systemBarsPadding() // 系统栏内边距
                    .padding(
                        // 根据顶部应用栏高度和胶囊按钮高度计算垂直内边距, 使胶囊按钮垂直居中
                        vertical = (TopAppBarDefaults.TopAppBarExpandedHeight - capsuleHeight) / 2,
                        // 水平内边距
                        horizontal = capsuleEdgePadding,
                    ),
                backdrop = backdrop, // Backdrop
            )
        }
    }
}

/**
 * OverlayLayer 的预览
 */
@Preview
@Composable
private fun OverlayLayerPreview() {
    TrebleKitTheme {
        OverlayLayer()
    }
}
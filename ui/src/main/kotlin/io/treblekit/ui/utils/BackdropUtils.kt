package io.treblekit.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

/**
 * 应用 LiquidGlass效果
 *
 * @param backdrop Backdrop
 * @param shape 形状
 * @param color 颜色
 * @param alpha 透明度
 * @param bigBlock 是否使用大块模糊
 */
fun Modifier.backdropEffects(
    backdrop: LayerBackdrop,
    shape: Shape,
    color: Color,
    alpha: Float,
    bigBlock: Boolean,
): Modifier = this@backdropEffects then Modifier.drawBackdrop(
    backdrop = backdrop,
    shape = { shape },
    effects = {
        vibrancy()
        blur(radius = 2f.dp.toPx())
        lens(
            refractionHeight = 12f.dp.toPx(),
            refractionAmount = 24f.dp.toPx(),
            depthEffect = bigBlock,
        )
    },
    onDrawSurface = {
        drawRect(
            color = color.copy(alpha = alpha),
            blendMode = BlendMode.Hue,
        )
    },
)
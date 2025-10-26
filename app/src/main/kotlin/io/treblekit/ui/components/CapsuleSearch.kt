package io.treblekit.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousCapsule
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.capsuleContainer
import io.treblekit.ui.theme.capsuleContent
import io.treblekit.ui.theme.capsuleHeight
import io.treblekit.ui.theme.capsuleWidth
import io.treblekit.ui.utils.NoOnClick

@Composable
fun CapsuleSearch(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
    onClick: () -> Unit = NoOnClick,
) {
    Box(
        modifier = modifier
            .width(width = capsuleWidth)
            .height(height = capsuleHeight)
            .drawBackdrop(
                backdrop = backdrop,
                shape = {
                    ContinuousCapsule
                },
                effects = {
                    vibrancy()
                    blur(radius = 2f.dp.toPx())
                    lens(
                        refractionHeight = 12f.dp.toPx(),
                        refractionAmount = 24f.dp.toPx(),
                        depthEffect = false,
                    )
                },
                onDrawSurface = {
                    drawRect(
                        color = capsuleContainer,
                        blendMode = BlendMode.Hue,
                    )
                },
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.size(size = 20.dp),
                tint = capsuleContent,
            )
            Text(
                text = "搜索",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 6.dp),
                color = capsuleContent,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun CapsuleSearchPreview() {
    TrebleKitTheme {
        CapsuleSearch()
    }
}
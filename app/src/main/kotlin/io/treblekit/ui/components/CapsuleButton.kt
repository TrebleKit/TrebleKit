package io.treblekit.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.capsule.ContinuousCapsule
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.capsuleContainer
import io.treblekit.ui.theme.capsuleContent
import io.treblekit.ui.theme.capsuleHeight
import io.treblekit.ui.theme.capsuleIndent
import io.treblekit.ui.theme.capsuleStroke
import io.treblekit.ui.theme.capsuleWidth
import io.treblekit.ui.utils.NoOnClick
import io.treblekit.ui.utils.backdropEffects

@Composable
fun CapsuleButton(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
) {
    Box(
        modifier = modifier
            .width(width = capsuleWidth)
            .height(height = capsuleHeight)
            .backdropEffects(
                backdrop = backdrop,
                shape = ContinuousCapsule,
                color = capsuleContainer,
                alpha = capsuleContainer.alpha,
                bigBlock = false,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize()
                    .clickable(onClick = NoOnClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreHoriz,
                    contentDescription = null,
                    tint = capsuleContent,
                )
            }
            VerticalDivider(
                modifier = Modifier
                    .padding(vertical = capsuleIndent)
                    .wrapContentWidth()
                    .fillMaxHeight(),
                color = capsuleStroke,
            )
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize()
                    .clickable(onClick = NoOnClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = capsuleContent,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CapsuleButtonPreview() {
    TrebleKitTheme {
        CapsuleButton()
    }
}
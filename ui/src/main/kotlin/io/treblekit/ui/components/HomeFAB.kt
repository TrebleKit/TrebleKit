package io.treblekit.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.utils.NoOnClick
import io.treblekit.ui.utils.backdropEffects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFAB(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
    onClick: () -> Unit = NoOnClick,
) {
    val tooltipState = rememberTooltipState()
    TooltipBox(
        modifier = modifier.wrapContentSize(),
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Above,
            spacingBetweenTooltipAndAnchor = 8.dp
        ),
        tooltip = {
            PlainTooltip {
                Text(
                    text = "FAB",
                    modifier = Modifier.padding(all = 4.dp),
                )
            }
        },
        state = tooltipState,
    ) {
        ExtendedFloatingActionButton(
            text = {
                Text(
                    text = "返回",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.TwoTone.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            onClick = onClick,
            modifier = Modifier
                .wrapContentSize()
                .backdropEffects(
                    backdrop = backdrop,
                    shape = ContinuousRoundedRectangle(size = 16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    alpha = 0.8f,
                    bigBlock = false,
                ),
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
        )
    }
}

@Preview
@Composable
fun HomeFABPreview() {
    TrebleKitTheme {
        HomeFAB()
    }
}
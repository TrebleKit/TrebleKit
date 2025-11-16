package io.treblekit.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.treblekit.ui.factory.IViewFactory
import io.treblekit.ui.factory.LocalViewFactory
import io.treblekit.ui.theme.capsuleEdgePadding
import io.treblekit.ui.theme.capsuleWidth

@Composable
@ReadOnlyComposable
fun rememberCapsulePadding(excess: Dp = 0.dp): PaddingValues {
    val viewFactory: IViewFactory? = LocalViewFactory.current
    val density: Density = LocalDensity.current
    val inspection: Boolean = LocalInspectionMode.current
    val basePadding = capsuleWidth + capsuleEdgePadding
    return PaddingValues(
        end = when {
            inspection -> basePadding - excess

            viewFactory != null -> with(receiver = density) {
                val systemPadding = viewFactory.overlayView.paddingRight.toDp()
                return@with basePadding + systemPadding - excess
            }

            else -> basePadding - excess
        },
    )
}
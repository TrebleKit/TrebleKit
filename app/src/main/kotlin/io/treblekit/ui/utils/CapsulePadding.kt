package io.treblekit.ui.utils

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.treblekit.common.FactoryHost
import io.treblekit.ui.theme.capsuleEdgePadding
import io.treblekit.ui.theme.capsuleWidth

@Composable
@ReadOnlyComposable
fun rememberCapsulePadding(excess: Dp = 0.dp): PaddingValues {
    val activity: Activity? = LocalActivity.current
    val density: Density = LocalDensity.current
    val inspection: Boolean = LocalInspectionMode.current
    val basePadding = capsuleWidth + capsuleEdgePadding
    return PaddingValues(
        end = when {
            inspection -> basePadding - excess

            activity != null && activity is FactoryHost -> with(receiver = density) {
                val factory = (activity as? FactoryHost)?.getViewFactory
                val system = (factory?.overlayView?.paddingRight ?: 0).toDp()
                return@with (basePadding + system) - excess
            }

            else -> basePadding - excess
        },
    )
}
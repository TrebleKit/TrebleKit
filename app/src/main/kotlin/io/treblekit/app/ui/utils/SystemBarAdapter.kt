package io.treblekit.app.ui.utils

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.core.view.WindowInsetsControllerCompat
import io.treblekit.app.hybrid.FlutterUiController

@Composable
@NonRestartableComposable
fun SystemBarAdapter(isLight: Boolean) {
    val inspection: Boolean = LocalInspectionMode.current
    val activity: Activity? = LocalActivity.current
    LaunchedEffect(key1 = isLight) {
        if (!inspection && activity != null) {
            WindowInsetsControllerCompat(
                activity.window,
                activity.window.decorView,
            ).apply {
                isAppearanceLightStatusBars = isLight
                isAppearanceLightNavigationBars = isLight
            }
            FlutterUiController().apply {
                isAppearanceLightSystemBars(isLight = isLight)
            }
        }
    }
}
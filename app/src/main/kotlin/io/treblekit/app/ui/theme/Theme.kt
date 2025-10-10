package io.treblekit.app.ui.theme

import android.app.Activity
import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun TrebleKitTheme(content: @Composable () -> Unit) {
    val context: Context = LocalContext.current
    val activity: Activity? = LocalActivity.current
    val inspection: Boolean = LocalInspectionMode.current

    if (!inspection && activity != null) {
        WindowInsetsControllerCompat(
            activity.window,
            activity.window.decorView,
        ).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = dynamicDarkColorScheme(context),
        typography = AppTypography,
        content = content,
    )
}
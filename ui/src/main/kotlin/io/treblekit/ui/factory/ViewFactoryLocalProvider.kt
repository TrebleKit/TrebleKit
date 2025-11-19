package io.treblekit.ui.factory

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal fun ViewFactoryLocalProvider(content: @Composable () -> Unit) {
    val activity: Activity? = LocalActivity.current
    val factory = ViewFactoryImpl(
        context = activity ?: error(
            message = "Activity is null",
        ),
    )
    CompositionLocalProvider(
        value = LocalViewFactory provides factory,
        content = content,
    )
}
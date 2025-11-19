package io.treblekit.ui.factory

import androidx.compose.runtime.compositionLocalOf

internal val LocalViewFactory = compositionLocalOf<IViewFactory?> {
    return@compositionLocalOf null
}
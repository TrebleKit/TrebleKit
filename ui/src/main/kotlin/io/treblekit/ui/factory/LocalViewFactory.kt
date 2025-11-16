package io.treblekit.ui.factory

import androidx.compose.runtime.compositionLocalOf

val LocalViewFactory = compositionLocalOf<IViewFactory?> {
    return@compositionLocalOf null
}
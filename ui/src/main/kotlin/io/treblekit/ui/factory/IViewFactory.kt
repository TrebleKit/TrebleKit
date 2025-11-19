package io.treblekit.ui.factory

import android.view.View

internal interface IViewFactory {
    val overlayView: View
    val wrapperView: View
    val effectView: View
}
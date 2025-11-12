package io.treblekit.common

import android.view.View

interface IViewFactory {
    val contentView: View
    val overlayView: View
    val wrapperView: View
    val effectView: View
}

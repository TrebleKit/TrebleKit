package io.treblekit.common

import android.view.View
import io.treblekit.ui.view.HybridComposeView
import io.treblekit.ui.view.OverlayView
import io.treblekit.ui.view.StreamerEffectView

interface IViewFactory {
    val contentView: HybridComposeView
    val overlayView: OverlayView
    val wrapperView: View
    val effectView: StreamerEffectView
}

package io.treblekit.common

import io.treblekit.ui.view.StreamerEffectView
import io.treblekit.ui.view.FlutterWrapperView
import io.treblekit.ui.view.HybridComposeView
import io.treblekit.ui.view.OverlayView

interface IViewFactory {
    val contentView: HybridComposeView
    val overlayView: OverlayView
    val wrapperView: FlutterWrapperView
    val effectView: StreamerEffectView
}

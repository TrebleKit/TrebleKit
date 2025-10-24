package io.treblekit.common

import io.treblekit.ui.view.StreamerEffectView
import io.treblekit.ui.view.FlutterWrapperView
import io.treblekit.ui.view.HybridComposeView
import io.treblekit.ui.view.OverlayView

interface IViewFactory {
    val getContentView: HybridComposeView
    val getOverlayView: OverlayView
    val getWrapperView: FlutterWrapperView
    val getEffectView: StreamerEffectView
}

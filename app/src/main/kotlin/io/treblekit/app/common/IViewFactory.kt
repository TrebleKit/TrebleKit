package io.treblekit.app.common

import io.treblekit.app.ui.view.EffectView
import io.treblekit.app.ui.view.FlutterWrapperView
import io.treblekit.app.ui.view.HybridComposeView
import io.treblekit.app.ui.view.OverlayView

interface IViewFactory {
    val getContentView: HybridComposeView
    val getOverlayView: OverlayView
    val getWrapperView: FlutterWrapperView
    val getEffectView: EffectView
}

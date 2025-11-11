package io.treblekit.ui.factory

import android.content.Context
import io.treblekit.common.IViewFactory
import io.treblekit.ui.view.StreamerEffectView
import io.treblekit.ui.view.FlutterWrapperView
import io.treblekit.ui.view.HybridComposeView
import io.treblekit.ui.view.OverlayView

class ViewFactory(context: Context) : IViewFactory {

    override val contentView: HybridComposeView by lazy {
        return@lazy HybridComposeView(context = context)
    }

    override val overlayView: OverlayView by lazy {
        return@lazy OverlayView(context = context)
    }

    override val effectView: StreamerEffectView by lazy {
        return@lazy StreamerEffectView(context = context)
    }

    override val wrapperView: FlutterWrapperView by lazy {
        return@lazy FlutterWrapperView(context = context)
    }
}
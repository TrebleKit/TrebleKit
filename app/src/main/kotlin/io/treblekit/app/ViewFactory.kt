package io.treblekit.app

import android.content.Context
import android.view.View
import io.treblekit.common.IViewFactory
import io.treblekit.hybrid.wrapper.FlutterWrapperView
import io.treblekit.ui.view.HybridComposeView
import io.treblekit.ui.view.OverlayView
import io.treblekit.ui.view.StreamerEffectView

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

    override val wrapperView: View by lazy {
        return@lazy FlutterWrapperView(context = context)
    }
}
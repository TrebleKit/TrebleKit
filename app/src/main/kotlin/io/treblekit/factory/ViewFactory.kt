package io.treblekit.factory

import androidx.fragment.app.FragmentActivity
import io.treblekit.common.IViewFactory
import io.treblekit.ui.view.StreamerEffectView
import io.treblekit.ui.view.FlutterWrapperView
import io.treblekit.ui.view.HybridComposeView
import io.treblekit.ui.view.OverlayView

class ViewFactory(activity: FragmentActivity) : IViewFactory {

    override val getContentView: HybridComposeView by lazy {
        return@lazy HybridComposeView(context = activity)
    }

    override val getOverlayView: OverlayView by lazy {
        return@lazy OverlayView(context = activity)
    }

    override val getEffectView: StreamerEffectView by lazy {
        return@lazy StreamerEffectView(context = activity)
    }

    override val getWrapperView: FlutterWrapperView by lazy {
        return@lazy FlutterWrapperView(context = activity)
    }
}
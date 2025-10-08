package io.treblekit.app.factory

import androidx.fragment.app.FragmentActivity
import io.treblekit.app.common.IViewFactory
import io.treblekit.app.ui.view.EffectView
import io.treblekit.app.ui.view.FlutterWrapperView
import io.treblekit.app.ui.view.HybridComposeView
import io.treblekit.app.ui.view.OverlayView

class ViewFactory(activity: FragmentActivity) : IViewFactory {

    override val getContentView: HybridComposeView by lazy {
        return@lazy HybridComposeView(context = activity)
    }

    override val getOverlayView: OverlayView by lazy {
        return@lazy OverlayView(context = activity)
    }

    override val getEffectView: EffectView by lazy {
        return@lazy EffectView(context = activity)
    }

    override val getWrapperView: FlutterWrapperView by lazy {
        return@lazy FlutterWrapperView(context = activity)
    }
}
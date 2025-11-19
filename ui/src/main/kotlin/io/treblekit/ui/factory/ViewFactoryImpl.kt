package io.treblekit.ui.factory

import android.content.Context
import android.view.View
import io.treblekit.ui.view.FlutterWrapperView
import io.treblekit.ui.view.OverlayView
import io.treblekit.ui.view.StreamerEffectView

internal class ViewFactoryImpl(context: Context) : IViewFactory {

    override val overlayView: View by lazy {
        return@lazy OverlayView(context = context)
    }

    override val effectView: View by lazy {
        return@lazy StreamerEffectView(context = context)
    }

    override val wrapperView: View by lazy {
        return@lazy FlutterWrapperView(context = context)
    }
}
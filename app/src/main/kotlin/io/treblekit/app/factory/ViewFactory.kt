package io.treblekit.app.factory

import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import io.treblekit.app.common.IViewFactory
import io.treblekit.app.hybrid.loadFlutterView
import io.treblekit.app.ui.view.EffectView
import io.treblekit.app.ui.view.HybridComposeView
import io.treblekit.app.ui.view.OverlayView

class ViewFactory(activity: FragmentActivity) : IViewFactory {

    override val getContentFrame: FrameLayout by lazy {
        return@lazy FrameLayout(activity)
    }

    override val getContentView: HybridComposeView by lazy {
        return@lazy HybridComposeView(context = activity)
    }

    override val getOverlayView: OverlayView by lazy {
        return@lazy OverlayView(context = activity)
    }

    override val getEffectView: EffectView by lazy {
        return@lazy EffectView(context = activity)
    }

    override val getFlutterView: ViewPager2 by lazy {
        return@lazy activity.loadFlutterView()
    }
}
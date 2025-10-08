package io.treblekit.app.common

import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import io.treblekit.app.ui.view.EffectView
import io.treblekit.app.ui.view.HybridComposeView
import io.treblekit.app.ui.view.OverlayView

interface IViewFactory {
    val getContentFrame: FrameLayout
    val getContentView: HybridComposeView
    val getOverlayView: OverlayView
    val getFlutterView: ViewPager2
    val getEffectView: EffectView
}
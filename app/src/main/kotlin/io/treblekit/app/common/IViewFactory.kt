package io.treblekit.app.common

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import io.treblekit.app.ui.view.EffectView
import io.treblekit.app.ui.view.HybridComposeView
import io.treblekit.app.ui.view.OverlayView

interface IViewFactory {
    val getContentView: HybridComposeView
    val getOverlayView: OverlayView
    val getToolbarView: MaterialToolbar
    val getFlutterView: ViewPager2
    val getEffectView: EffectView
}
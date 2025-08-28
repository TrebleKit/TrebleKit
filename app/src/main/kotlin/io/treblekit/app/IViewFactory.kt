package io.treblekit.app

import android.view.View
import android.widget.FrameLayout
import com.google.android.material.appbar.MaterialToolbar

interface IViewFactory {
    val getContentFrame: FrameLayout
    val getContentView: HybridComposeView
    val getOverlayView: OverlayView
    val getToolbarView: MaterialToolbar
    val getFlutterView: View
}
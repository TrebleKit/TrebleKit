package io.treblekit.app

import android.view.View
import io.treblekit.hybrid.base.HybridActivity
import io.treblekit.ui.UiAppView
import rikka.shizuku.Shizuku

class MainActivity : HybridActivity() {

    override fun onCreateView(): View {
        return UiAppView(
            context = this@MainActivity,
        )
    }
}
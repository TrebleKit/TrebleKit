package io.treblekit.app

import android.view.View
import io.treblekit.hybrid.base.HybridActivity
import io.treblekit.ui.view.HybridComposeView

class MainActivity : HybridActivity() {

    override fun onCreateView(): View {
        return HybridComposeView(
            context = this@MainActivity,
        )
    }
}
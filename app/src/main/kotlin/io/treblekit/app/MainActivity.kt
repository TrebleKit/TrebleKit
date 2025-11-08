package io.treblekit.app

import android.view.View
import androidx.compose.runtime.Composable
import io.treblekit.common.FactoryHost
import io.treblekit.common.IViewFactory
import io.treblekit.hybrid.base.HybridActivity
import io.treblekit.ui.activity.ActivityMain
import io.treblekit.ui.factory.ViewFactory
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.view.HybridComposeView

class MainActivity : HybridActivity(), FactoryHost {

    override fun onCreateView(): View {
        return getViewFactory.contentView
    }

    override val getViewFactory: IViewFactory by lazy {
        return@lazy ViewFactory(context = this@MainActivity)
    }

    override fun onViewCreated(view: View?) {
        if (view != null && view is HybridComposeView) {
            view.setContent {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        TrebleKitTheme {
            ActivityMain()
        }
    }
}
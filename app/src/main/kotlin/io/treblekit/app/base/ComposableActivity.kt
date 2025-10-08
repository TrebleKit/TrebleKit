package io.treblekit.app.base

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.UiComposable
import io.treblekit.app.common.FactoryHost
import io.treblekit.app.common.IViewFactory
import io.treblekit.app.factory.ViewFactory
import io.treblekit.app.hybrid.HybridActivity

abstract class ComposableActivity : HybridActivity(), FactoryHost {

    override fun resetContentView(): View? {
        return getViewFactory.getContentView
    }

    override val getViewFactory: IViewFactory by lazy {
        return@lazy ViewFactory(this@ComposableActivity)
    }

    override fun initViews() {
        super.initViews()
        getViewFactory.getContentView.setContent {
            Content()
        }
    }

    @Composable
    @UiComposable
    abstract fun Content()
}
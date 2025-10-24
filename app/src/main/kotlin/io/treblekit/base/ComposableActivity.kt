package io.treblekit.base

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.UiComposable
import io.treblekit.common.FactoryHost
import io.treblekit.common.IViewFactory
import io.treblekit.factory.ViewFactory
import io.treblekit.hybrid.HybridActivity

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
package io.treblekit.app.base

import android.view.View
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.UiComposable
import io.treblekit.app.common.FactoryHost
import io.treblekit.app.common.IViewFactory
import io.treblekit.app.factory.ViewFactory
import io.treblekit.app.hybrid.HybridActivity

abstract class ComposableActivity : HybridActivity(), FactoryHost {

    override fun resetContentView(): View? {
        return getViewFactory.getContentFrame
    }

    override val getViewFactory: IViewFactory by lazy {
        return@lazy ViewFactory(activity = this@ComposableActivity)
    }

    override fun initViews() {
        super.initViews()

        getViewFactory.getContentFrame.addView(
            getViewFactory.getContentView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
            )
        )

        getViewFactory.getContentView.setContent {
            Content()
        }
    }

    @Composable
    @UiComposable
    abstract fun Content()
}
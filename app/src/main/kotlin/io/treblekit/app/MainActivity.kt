package io.treblekit.app

import android.view.View
import io.treblekit.common.FactoryHost
import io.treblekit.common.IViewFactory
import io.treblekit.hybrid.base.HybridActivity
import io.treblekit.hybrid.wrapper.FlutterWrapperView
import io.treblekit.ui.activity.ActivityMain
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.view.HybridComposeView
import io.treblekit.ui.view.OverlayView
import io.treblekit.ui.view.StreamerEffectView

class MainActivity : HybridActivity(), FactoryHost {

    override val getViewFactory: IViewFactory = object : IViewFactory {

        override val contentView: View by lazy {
            return@lazy HybridComposeView(context = this@MainActivity)
        }

        override val overlayView: View by lazy {
            return@lazy OverlayView(context = this@MainActivity)
        }

        override val effectView: View by lazy {
            return@lazy StreamerEffectView(context = this@MainActivity)
        }

        override val wrapperView: View by lazy {
            return@lazy FlutterWrapperView(context = this@MainActivity)
        }
    }

    override fun onCreateView(): View {
        return getViewFactory.contentView
    }

    override fun onViewCreated(view: View?) {
        if (view != null && view is HybridComposeView) {
            view.setContent {
                TrebleKitTheme {
                    ActivityMain()
                }
            }
        }
    }
}
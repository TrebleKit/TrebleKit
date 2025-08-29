package io.treblekit.app

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.appbar.MaterialToolbar
import com.wyq0918dev.flutter_mixed.FlutterMixedPlugin
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.ui.ActivityMain
import io.treblekit.app.ui.theme.TrebleKitTheme

class MainActivity : AppCompatActivity() {


    private var mFlutterFragment: FlutterFragment? = null
    private var mFlutterView: View? = null

    private val mFactory: IViewFactory = object : IViewFactory {

        override val getContentFrame: FrameLayout by lazy {
            return@lazy FrameLayout(this@MainActivity)
        }
        override val getContentView: HybridComposeView by lazy {
            return@lazy HybridComposeView(this@MainActivity)
        }
        override val getOverlayView: OverlayView by lazy {
            return@lazy OverlayView(this@MainActivity)
        }
        override val getToolbarView: MaterialToolbar by lazy {
            return@lazy MaterialToolbar(this@MainActivity)
        }
        override val getFlutterView: View by lazy {
            return@lazy mFlutterView ?: View(this@MainActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersive()

        FlutterMixedPlugin.loadFlutter(
            activity = this@MainActivity
        ) { fragment, view ->
            mFlutterFragment = fragment
            mFlutterView = view
        }

        val frame = FrameLayout(this@MainActivity)
        val comp = HybridComposeView(this@MainActivity)
        val overlay = OverlayView(this@MainActivity)

        overlay.setMenuOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "overlay menu",
                Toast.LENGTH_SHORT,
            ).show()
        }

        overlay.setCloseOnClickListener {
            Toast.makeText(
                this@MainActivity,
                "overlay close",
                Toast.LENGTH_SHORT,
            ).show()
        }

        comp.setContent {
            TrebleKitTheme {
                ActivityMain(flutter = mFlutterView)
            }
        }

        frame.addView(comp)
        frame.addView(overlay)

        setContentView(frame)
    }

    /**
     * 导航栏状态栏沉浸
     */
    private fun immersive() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb(),
            ),
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
    }
}
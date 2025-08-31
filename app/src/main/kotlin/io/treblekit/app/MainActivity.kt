package io.treblekit.app

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.appbar.MaterialToolbar
import com.wyq0918dev.flutter_mixed.FlutterMixedPlugin
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.ui.ActivityMain
import io.treblekit.app.ui.IViewFactory
import io.treblekit.app.ui.theme.TrebleKitTheme

class MainActivity : AppCompatActivity() {

    private var mFlutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersive()

        var flutterView: View? = null

        val factory: IViewFactory = object : IViewFactory {

            override val getToolbarView: MaterialToolbar by lazy {
                return@lazy MaterialToolbar(this@MainActivity)
            }

            override val getFlutterView: View by lazy {
                return@lazy flutterView ?: View(this@MainActivity)
            }
        }

        FlutterMixedPlugin.loadFlutter(
            activity = this@MainActivity
        ) { fragment, view ->
            mFlutterFragment = fragment
            flutterView = view
        }

        setSupportActionBar(factory.getToolbarView)

        setContent {
            TrebleKitTheme {
                ActivityMain(factory = factory)
            }
        }
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
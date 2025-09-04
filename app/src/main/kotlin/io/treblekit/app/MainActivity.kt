package io.treblekit.app

import android.content.Intent
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
import io.treblekit.app.ui.activity.ActivityMain
import io.treblekit.app.ui.components.IViewFactory
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
            TrebleKitTheme(dynamicColor = false) {
                ActivityMain(factory = factory)
            }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        mFlutterFragment?.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mFlutterFragment?.onNewIntent(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        mFlutterFragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mFlutterFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        mFlutterFragment?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mFlutterFragment?.onTrimMemory(level)
    }

    override fun onDestroy() {
        super.onDestroy()
        mFlutterFragment = null
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
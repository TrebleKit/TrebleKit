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
import io.flutter.embedding.android.RenderMode
import io.treblekit.app.ui.activity.ActivityMain
import io.treblekit.app.ui.components.IViewFactory
import io.treblekit.app.ui.theme.TrebleKitTheme

class MainActivity : AppCompatActivity(), IViewFactory {

    private var mFlutterFragment: FlutterFragment? = null

    override val getToolbarView: MaterialToolbar by lazy {
        return@lazy MaterialToolbar(this@MainActivity).let {
            setSupportActionBar(it)
            return@let it
        }
    }

    override val getFlutterView: View by lazy {
        return@lazy FlutterMixedPlugin.loadFlutter(
            activity = this@MainActivity,
            renderMode = RenderMode.texture, // 混合开发
        ) { fragment, view ->
            mFlutterFragment = fragment
            return@loadFlutter view
        }.let { flutter ->
            return@let flutter ?: View(
                this@MainActivity,
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        setContent {
            TrebleKitTheme(dynamicColor = false) {
                ActivityMain(factory = this)
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
}
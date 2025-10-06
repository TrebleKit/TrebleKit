package io.treblekit.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.core.view.WindowCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.common.IViewFactory
import io.treblekit.app.hybrid.FlutterAdapter
import io.treblekit.app.hybrid.loadFragment
import io.treblekit.app.ui.activity.ActivityMain
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.view.EffectView
import io.treblekit.app.ui.view.HybridComposeView
import io.treblekit.app.ui.view.OverlayView


class MainActivity : AppCompatActivity(), IViewFactory {

    private var mFlutterFragment: FlutterFragment? = null

    override val getContentView: HybridComposeView by lazy {
        return@lazy HybridComposeView(
            context = this@MainActivity,
        ).let { content ->
            setContentView(content)
            return@let content
        }
    }

    override val getOverlayView: OverlayView by lazy {
        return@lazy OverlayView(
            context = this@MainActivity,
        )
    }

    override val getToolbarView: MaterialToolbar by lazy {
        return@lazy MaterialToolbar(
            this@MainActivity,
        ).apply {
            setTitleTextColor(Color.White.toArgb())
        }.let { toolbar ->
            setSupportActionBar(toolbar)
            return@let toolbar
        }
    }

    override val getEffectView: EffectView by lazy {
        return@lazy EffectView(
            context = this@MainActivity,
        )
    }

    override val getFlutterView: ViewPager2 by lazy {
        return@lazy ViewPager2(
            this@MainActivity,
        ).apply {
            isUserInputEnabled = false
            adapter = FlutterAdapter(
                activity = this@MainActivity,
                flutter = mFlutterFragment,
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edgeToEdge()

        mFlutterFragment = loadFragment()
        getContentView.setContent {
            TrebleKitTheme {
                ActivityMain()
            }
        }
    }

    private fun edgeToEdge() {
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
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.isNavigationBarContrastEnforced = false
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
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mFlutterFragment?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        mFlutterFragment?.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        mFlutterFragment?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mFlutterFragment?.onTrimMemory(level)
    }

    @Preview(
        device = "id:pixel_9",
        apiLevel = 36,
        showSystemUi = true,
        showBackground = true,
        wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    )
    @Composable
    private fun MainActivityPreview() {
        TrebleKitTheme {
            ActivityMain()
        }
    }
}
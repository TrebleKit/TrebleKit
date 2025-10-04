package io.treblekit.app

import android.os.Build
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import io.treblekit.app.base.ComposableActivity
import io.treblekit.app.ui.ActivityMain
import io.treblekit.app.ui.theme.TrebleKitTheme


class MainActivity : ComposableActivity() {

    override val getEffectView: View by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return@lazy EffectView(this@MainActivity)
        }
        return@lazy View(this@MainActivity)
    }

    @Composable
    override fun Content() {
        TrebleKitTheme {
            ActivityMain()
        }
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
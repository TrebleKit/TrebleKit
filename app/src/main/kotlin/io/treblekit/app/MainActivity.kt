package io.treblekit.app

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import io.treblekit.base.ComposableActivity
import io.treblekit.common.IViewFactory
import io.treblekit.factory.ViewFactory
import io.treblekit.ui.activity.ActivityMain
import io.treblekit.ui.theme.TrebleKitTheme

class MainActivity : ComposableActivity() {

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
        uiMode = Configuration.UI_MODE_NIGHT_YES,
    )
    @Composable
    private fun MainActivityPreview() {
        TrebleKitTheme {
            ActivityMain()
        }
    }
}
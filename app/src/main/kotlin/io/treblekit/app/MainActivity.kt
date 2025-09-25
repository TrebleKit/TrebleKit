package io.treblekit.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import io.treblekit.app.base.ComposableActivity
import io.treblekit.app.ui.ActivityMain

class MainActivity : ComposableActivity() {

    @Composable
    override fun Content() {
        ActivityMain()
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
        ActivityMain()
    }
}
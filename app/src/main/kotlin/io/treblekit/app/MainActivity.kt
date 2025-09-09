package io.treblekit.app

import androidx.compose.runtime.Composable
import io.treblekit.app.ui.activity.ActivityMain
import io.treblekit.app.ui.theme.TrebleKitTheme

class MainActivity : ComposableActivity() {

    @Composable
    override fun Content() {
        TrebleKitTheme {
            ActivityMain()
        }
    }
}
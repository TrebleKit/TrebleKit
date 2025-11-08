package io.treblekit.app

import androidx.compose.runtime.Composable
import io.treblekit.base.ComposableActivity
import io.treblekit.ui.activity.ActivityMain
import io.treblekit.ui.theme.TrebleKitTheme

class MainActivity : ComposableActivity() {

    @Composable
    override fun Content() {
        TrebleKitTheme {
            ActivityMain()
        }
    }
}
package io.treblekit.app

import android.os.Bundle
import androidx.core.view.WindowCompat
import io.flutter.embedding.android.FlutterActivity
import io.treblekit.app.hybrid.MultipleConfig

class NormalFlutterActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.isNavigationBarContrastEnforced = false
    }

    override fun getCachedEngineId(): String? {
        return MultipleConfig.NORMAL.engineId
    }
}
package io.treblekit.app

import android.os.Bundle
import androidx.core.view.WindowCompat
import io.flutter.embedding.android.FlutterActivity
import io.treblekit.app.hybrid.FLOAT_ENGINE_ID

class FloatFlutterActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.isNavigationBarContrastEnforced = false
    }

    override fun getCachedEngineId(): String? {
        return FLOAT_ENGINE_ID
    }
}
package io.treblekit.app.hybrid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterEngineConfigurator
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine

abstract class HybridActivity : AppCompatActivity(), IViewFactory, FlutterEngineConfigurator {
    private var mFlutterFragment: FlutterFragment? = null

    override val getFlutterView: View by lazy {
        return@lazy loadFlutterView(
            flutter = mFlutterFragment,
        )
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {

    }

    override fun cleanUpFlutterEngine(flutterEngine: FlutterEngine) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFlutterFragment = loadFlutterFragment()
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
}
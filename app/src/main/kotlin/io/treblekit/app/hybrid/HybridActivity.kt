package io.treblekit.app.hybrid

import android.content.Intent
import io.flutter.embedding.android.FlutterFragment
import io.treblekit.app.base.BaseActivity
import io.treblekit.app.common.FlutterHost

abstract class HybridActivity : BaseActivity(), FlutterHost {

    private var mFlutterFragment: FlutterFragment? = null

    override val getFlutterFragment: FlutterFragment?
        get() = mFlutterFragment

    override fun initFlutter() {
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
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mFlutterFragment?.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        mFlutterFragment?.onActivityResult(
            requestCode, resultCode, data
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
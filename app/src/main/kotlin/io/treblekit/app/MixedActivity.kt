package io.treblekit.app

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wyq0918dev.flutter_mixed.FlutterMixedPlugin
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.treblekit.common.IViewFactory

abstract class MixedActivity : AppCompatActivity(), IViewFactory {

    private var mFlutterFragment: FlutterFragment? = null

    override val getFlutterView: View by lazy {
        return@lazy FlutterMixedPlugin.loadFlutter(
            activity = this@MixedActivity,
            renderMode = RenderMode.texture, // 混合开发
        ) { fragment, view ->
            mFlutterFragment = fragment
            return@loadFlutter view
        }.let { flutter ->
            return@let flutter ?: View(this@MixedActivity)
        }
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
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        mFlutterFragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mFlutterFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        mFlutterFragment?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mFlutterFragment?.onTrimMemory(level)
    }

    override fun onDestroy() {
        super.onDestroy()
        mFlutterFragment = null
    }
}
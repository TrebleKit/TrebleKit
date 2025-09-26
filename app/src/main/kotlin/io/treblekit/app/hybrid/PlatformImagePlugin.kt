package io.treblekit.app.hybrid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.io.ByteArrayOutputStream
import androidx.core.graphics.createBitmap
import io.flutter.plugin.common.EventChannel
import java.util.concurrent.atomic.AtomicBoolean

class PlatformImagePlugin : FlutterPlugin, MethodChannel.MethodCallHandler {
    private var channel: MethodChannel? = null
    private var context: Context? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        channel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_platform_image")
        channel?.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (context == null) return
        when (call.method) {
            "drawableMipmap" -> {
                val name: String? = call.argument("name")
                val isDrawable: Boolean = call.argument("is_drawable") ?: false
                val id: Int? = context?.resources?.getIdentifier(
                    name,
                    if (isDrawable) "drawable" else "mipmap",
                    context?.packageName
                )
                val drawable: Drawable? = ContextCompat.getDrawable(context!!, id!!)
                val byteArray = drawableToByteArray(drawable)
                result.success(byteArray)
            }
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel?.setMethodCallHandler(null)
    }

    private fun drawableToByteArray(drawable: Drawable?): ByteArray {
        if (drawable == null) return ByteArray(size = 0)
        val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}

/** 事件通道事件接收器 */
private var mEventSink: EventChannel.EventSink? = null

/** 事件通道监听状态 */
private val mListeningState = AtomicBoolean(false)

class AndroidToFlutter : FlutterPlugin, EventChannel.StreamHandler {
    /** Flutter 事件通道 */
    private lateinit var mEventChannel: EventChannel



    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        // 初始化事件通道
        mEventChannel = EventChannel(binding.binaryMessenger, "android_to_flutter")
        // 设置事件通道流处理
        mEventChannel.setStreamHandler(this@AndroidToFlutter)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mEventChannel.setStreamHandler(null)
    }

    override fun onListen(
        arguments: Any?,
        events: EventChannel.EventSink?
    ) {
        if (events != null) mEventSink = events
        else {
            Log.e("TAG", "EventSink 为空")
        }
        if (mListeningState.getAndSet(events != null)) {
            Log.w("TAG", "已在监听事件，忽略重复请求")
            return
        }
    }

    override fun onCancel(arguments: Any?) {
        if (mListeningState.getAndSet(false)) {
            mEventSink = null
        }
    }

}

fun sendData(data: String) {
    if (mListeningState.get()) {
        try {
            mEventSink?.success(
                mapOf(
                    "type" to "takeString",
                    "data" to data,
                )
            )
        } catch (e: Exception) {
            mEventSink?.error(
                "GODOT_EVENT_ERROR",
                e.message,
                null,
            )
        }
    }
}

class FlutterUiController {
    fun isAppearanceLightSystemBars(isLight: Boolean) {
        sendData(data = if (isLight) "dark_icon" else "light_icon")
    }
}
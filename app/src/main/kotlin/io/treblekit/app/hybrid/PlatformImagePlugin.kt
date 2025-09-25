package io.treblekit.app.hybrid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.io.ByteArrayOutputStream
import androidx.core.graphics.createBitmap

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
        if (call.method.equals("drawableMipmap")) {
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
        } else {
            result.notImplemented()
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
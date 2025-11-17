package io.treblekit.hybrid.plugin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.createBitmap
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.treblekit.resources.ResourcesLogo
import java.io.ByteArrayOutputStream

internal class PlatformResources : FlutterPlugin, MethodChannel.MethodCallHandler {

    /** 方法通道 */
    private lateinit var mChannel: MethodChannel

    /** 应用程序上下文 */
    private lateinit var mContext: Context

    /**
     * 附加到引擎
     */
    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mContext = binding.applicationContext
        mChannel = MethodChannel(binding.binaryMessenger, PLATFORM_RESOURCES_CHANNEL)
        mChannel.setMethodCallHandler(this)
    }

    /**
     * 从引擎分离
     */
    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mChannel.setMethodCallHandler(null)
    }

    /**
     * 方法调用
     */
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "treblekit" -> result.success(drawableToByteArray(id = ResourcesLogo.TREBLEKIT_LOGO))
            "freefeos" -> result.success(drawableToByteArray(id = ResourcesLogo.FREEFEOS_LOGO))
            "ecosedkit" -> result.success(drawableToByteArray(id = ResourcesLogo.ECOSEDKIT_LOGO))
            "ebkit" -> result.success(drawableToByteArray(id = ResourcesLogo.EBKIT_LOGO))
            else -> result.notImplemented()
        }
    }

    /**
     * 绘制Drawable为PNG格式二进制数据
     */
    private fun drawableToByteArray(id: Int): ByteArray {
        val drawable: Drawable? = AppCompatResources.getDrawable(mContext, id)
        if (drawable != null) {
            val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        } else {
            return ByteArray(size = 0)
        }
    }

    /**
     * 伴生对象
     */
    private companion object {

        /** 平台资源插件通道名称 */
        private const val PLATFORM_RESOURCES_CHANNEL: String = "platform_resources"
    }
}
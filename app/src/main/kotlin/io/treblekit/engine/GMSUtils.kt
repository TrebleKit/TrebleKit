package io.treblekit.engine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.AppUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import io.treblekit.engine.Engine.Companion.TAG

/**
 * 判断是否支持谷歌基础服务
 */
fun isSupportGMS(context: Context): Boolean {
    return if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
            context
        ) == ConnectionResult.SUCCESS
    ) true else AppUtils.isAppInstalled(
        EcosedManifest.GMS_PACKAGE
    )
}

fun gms(context: Context) {
    try {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setPackage(EcosedManifest.GMS_PACKAGE)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.w(TAG, "不存在可启动的默认活动")
            intent.setClassName(
                EcosedManifest.GMS_PACKAGE,
                context.packageManager.resolveActivity(intent, 0)!!.activityInfo.name
            )
            context.startActivity(intent)
        }
        Toast.makeText(context, "已安装", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Log.w(TAG, "microG设置启动失败", e)
        Toast.makeText(context, "未安装", Toast.LENGTH_LONG).show()
    }
}
package io.treblekit.engine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.AppUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

/** 谷歌基础服务包名 */
private const val GMS_PACKAGE: String = "com.google.android.gms"

private const val GMS_CLASS: String = "com.google.android.gms.app.settings.GoogleSettingsLink"

private const val TAG: String = "GMSUtils"

/**
 * 判断是否支持谷歌基础服务
 */
fun Context.isSupportGMS(): Boolean {
    return if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
            this@isSupportGMS
        ) == ConnectionResult.SUCCESS
    ) true else AppUtils.isAppInstalled(
        GMS_PACKAGE
    )
}

fun Context.launchGMS() {
    try {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setPackage(GMS_PACKAGE)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.w(TAG, "不存在可启动的默认活动")
            intent.setClassName(
                GMS_PACKAGE,
                packageManager.resolveActivity(intent, 0)!!.activityInfo.name
            )
            startActivity(intent)
        }
        Toast.makeText(this@launchGMS, "已安装", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Log.w(TAG, "microG设置启动失败", e)
        Toast.makeText(this@launchGMS, "未安装", Toast.LENGTH_LONG).show()
    }
}
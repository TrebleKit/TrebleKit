package io.treblekit.app

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.treblekit.aidl.ITrebleKit

class MainService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        return object : ITrebleKit.Stub() {

        }
    }

    override fun onCreate() {
        super.onCreate()
    }


}
package io.treblekit.app

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TreblePlatform : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
package io.treblekit.app

import android.app.Application
import com.google.android.material.color.DynamicColors

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        loadFlutter()
        DynamicColors.applyToActivitiesIfAvailable(this@MainApp)
    }
}
package io.treblekit.app

import android.app.Application
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

const val ENGINE_ID: String = "treblekit_flutter"

fun Application.loadFlutter(): FlutterEngine {
    return FlutterEngine(this@loadFlutter).let { engine ->
        val entry = DartExecutor.DartEntrypoint.createDefault()
        engine.dartExecutor.executeDartEntrypoint(entry)
        FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
        return@let engine
    }
}

fun loadFlutterFragment(): FlutterFragment {
    return FlutterFragment
        .withCachedEngine(ENGINE_ID)
        .renderMode(RenderMode.texture)
        .build()
}
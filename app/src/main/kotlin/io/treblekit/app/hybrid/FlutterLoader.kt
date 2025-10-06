package io.treblekit.app.hybrid

import android.app.Application
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

/** Flutter引擎ID */
private const val ENGINE_ID: String = "treblekit_flutter"

/**
 * 初始化Flutter引擎
 */
fun Application.loadEngine() {
    FlutterEngine(this@loadEngine).let { engine ->
        val entry = DartExecutor.DartEntrypoint.createDefault()
        engine.dartExecutor.executeDartEntrypoint(entry)
        CustomPluginRegistrant().registerWith(engine = engine)
        FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
    }
}

/**
 * 加载Flutter片段
 */
fun loadFragment(): FlutterFragment {
    return FlutterFragment.withCachedEngine(
        ENGINE_ID,
    ).renderMode(
        RenderMode.texture,
    ).build()
}
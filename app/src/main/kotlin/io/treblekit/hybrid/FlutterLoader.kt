package io.treblekit.hybrid

import android.app.Application
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.treblekit.hybrid.config.EngineConfig
import io.treblekit.hybrid.register.CustomPluginRegistrant

/**
 * 初始化Flutter引擎
 */
fun Application.loadFlutterEngine() {
    FlutterEngine(this@loadFlutterEngine).let { engine ->
        engine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(),
                EngineConfig.ENTRYPOINT,
            )
        )
        CustomPluginRegistrant.registerWith(engine = engine)
        FlutterEngineCache.getInstance().put(EngineConfig.ENGINE_ID, engine)
    }
}

/**
 * 加载Flutter片段
 */
fun loadEmbedFragment(): FlutterFragment {
    return FlutterFragment.withCachedEngine(
        EngineConfig.ENGINE_ID,
    ).renderMode(
        RenderMode.texture,
    ).build()
}
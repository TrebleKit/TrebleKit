package io.treblekit.app.hybrid

import android.app.Application
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor

/**
 * 初始化Flutter引擎
 */
fun Application.loadFlutterEngine() {
    FlutterEngineGroup(this@loadFlutterEngine).let { group ->
        MultipleConfig.entries.forEach { config ->
            group.createAndRunEngine(
                this@loadFlutterEngine,
                DartExecutor.DartEntrypoint(
                    FlutterInjector.instance().flutterLoader().findAppBundlePath(),
                    when (config) {
                        MultipleConfig.EMBED -> MultipleConfig.EMBED.entrypoint
                        MultipleConfig.NORMAL -> MultipleConfig.NORMAL.entrypoint
                    },
                ),
            ).let { engine ->
                CustomPluginRegistrant().registerWith(engine = engine)
                FlutterEngineCache.getInstance().put(config.engineId, engine)
            }
        }
    }
}

/**
 * 加载Flutter片段
 */
fun loadFlutterFragment(): FlutterFragment {
    return FlutterFragment.withCachedEngine(
        MultipleConfig.EMBED.engineId,
    ).renderMode(
        RenderMode.texture,
    ).build()
}
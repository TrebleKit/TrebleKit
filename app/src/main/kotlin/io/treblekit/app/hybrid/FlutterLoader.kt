package io.treblekit.app.hybrid

import android.app.Application
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor

/** Flutter引擎ID */
const val EMBED_ENGINE_ID: String = "treblekit_flutter"
const val FLOAT_ENGINE_ID: String = "treblekit_flutter_float"

/**
 * 初始化Flutter引擎
 */
fun Application.loadFlutterEngine() {
//    val flutterLoader = FlutterInjector.instance().flutterLoader()
//    if (flutterLoader.initialized()) {
//        error(
//            message = "DartEntrypoint can only be created once a FlutterEngine is created."
//        )
//    }

    FlutterEngineGroup(this@loadFlutterEngine).let { group ->
        arrayListOf(
            EMBED_ENGINE_ID,
            FLOAT_ENGINE_ID
        ).forEach { engineId ->
            group.createAndRunEngine(
                this@loadFlutterEngine,
                when (engineId) {
                    EMBED_ENGINE_ID -> DartExecutor.DartEntrypoint(
                        FlutterInjector.instance().flutterLoader().findAppBundlePath(),
                        "mainEmbed",
                    )

                    FLOAT_ENGINE_ID -> DartExecutor.DartEntrypoint(
                        FlutterInjector.instance().flutterLoader().findAppBundlePath(),
                        "mainFloat",
                    )

                    else -> DartExecutor.DartEntrypoint.createDefault()
                },
            ).let { engine ->
                CustomPluginRegistrant().registerWith(engine = engine)
                FlutterEngineCache.getInstance().put(engineId, engine)
            }
        }
    }
}

/**
 * 加载Flutter片段
 */
fun loadFlutterFragment(): FlutterFragment {
    return FlutterFragment.withCachedEngine(
        EMBED_ENGINE_ID,
    ).renderMode(
        RenderMode.texture,
    ).build()
}
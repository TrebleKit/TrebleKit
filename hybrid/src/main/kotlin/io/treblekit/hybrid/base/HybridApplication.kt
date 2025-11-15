package io.treblekit.hybrid.base

import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.treblekit.base.BaseApplication
import io.treblekit.hybrid.config.EngineConfig
import io.treblekit.hybrid.register.CustomPluginRegistrant

abstract class HybridApplication : BaseApplication() {

    override fun onCreateFlutter() {
        FlutterEngine(this@HybridApplication).let { engine ->
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
}
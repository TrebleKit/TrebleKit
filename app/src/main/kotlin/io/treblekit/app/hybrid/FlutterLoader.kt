package io.treblekit.app.hybrid

import android.app.Application
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
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
fun Application.loadFlutter() {
    FlutterEngine(this@loadFlutter).let { engine ->
        val entry = DartExecutor.DartEntrypoint.createDefault()
        engine.dartExecutor.executeDartEntrypoint(entry)
        CustomPluginRegistrant().registerWith(engine = engine)
        FlutterEngineCache.getInstance().put(ENGINE_ID, engine)
    }
}

/**
 * 加载Flutter片段
 */
fun loadFlutterFragment(): FlutterFragment {
    return FlutterFragment.withCachedEngine(
        ENGINE_ID,
    ).renderMode(
        RenderMode.texture,
    ).build()
}

/**
 * 加载Flutter视图
 */
fun FragmentActivity.loadFlutterView(
    flutter: FlutterFragment?,
): ViewPager2 {
    return ViewPager2(this@loadFlutterView).apply {
        isUserInputEnabled = false
        adapter = FlutterAdapter(
            activity = this@loadFlutterView,
            flutter = flutter
        )
    }
}
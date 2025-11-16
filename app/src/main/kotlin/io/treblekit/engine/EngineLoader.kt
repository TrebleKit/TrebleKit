package io.treblekit.engine

fun loadTrebleEngine() {
    val engine: IEngine = TrebleEngine()
    engine.onCreateEngine()
}

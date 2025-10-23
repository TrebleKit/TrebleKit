package io.treblekit.app.hybrid

enum class MultipleConfig(
    val engineId: String,
    val entrypoint: String,
) {

    EMBED(
        engineId = "treblekit_flutter_embed",
        entrypoint = "embed",
    ),

    NORMAL(
        engineId = "treblekit_flutter_normal",
        entrypoint = "main",
    ),
}
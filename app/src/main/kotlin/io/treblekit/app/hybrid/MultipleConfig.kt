package io.treblekit.app.hybrid

enum class MultipleConfig(
    val engineId: String,
    val entrypoint: String,
) {

    Embed(
        engineId = "treblekit_flutter_embed",
        entrypoint = "mainEmbed",
    ),

    Float(
        engineId = "treblekit_flutter_float",
        entrypoint = "mainFloat",
    ),

    Normal(
        engineId = "treblekit_flutter_normal",
        entrypoint = "main",
    ),
}
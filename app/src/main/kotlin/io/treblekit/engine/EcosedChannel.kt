package io.treblekit.engine

/**
 * 通道
 */
object EcosedChannel {
    /** Flutter插件通道名称 */
    const val FLUTTER_CHANNEL_NAME: String = "flutter_ecosed"

    /** 引擎桥梁插件 */
    const val BRIDGE_CHANNEL_NAME: String = "ecosed_bridge"

    /** 引擎 */
    const val ENGINE_CHANNEL_NAME: String = "ecosed_engine"

    /** 服务调用插件 */
    const val INVOKE_CHANNEL_NAME: String = "ecosed_invoke"

    /** 服务代理插件 */
    const val DELEGATE_CHANNEL_NAME: String = "ecosed_delegate"
}
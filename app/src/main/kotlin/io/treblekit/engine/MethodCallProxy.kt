package io.treblekit.engine

import android.os.Bundle

/**
 * 方法调用代理
 */
interface MethodCallProxy {

    /** 方法名代理 */
    val methodProxy: String

    /** 传入参数代理 */
    val bundleProxy: Bundle
}

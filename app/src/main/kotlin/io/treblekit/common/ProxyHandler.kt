package io.treblekit.common

import io.treblekit.engine.MethodCallProxy
import io.treblekit.engine.ResultProxy

interface ProxyHandler {
    /** 方法调用 */
    fun onProxyMethodCall(
        call: MethodCallProxy,
        result: ResultProxy,
    )
}
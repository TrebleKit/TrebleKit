package io.treblekit.common

import io.treblekit.engine.MethodCallProxy
import io.treblekit.engine.ResultProxy

interface MethodCallProxyHandler {
    /** 方法调用 */
    fun onMethodCall(
        call: MethodCallProxy,
        result: ResultProxy,
    )
}
package io.treblekit.common

/**
 * 代理处理接口
 */
interface ProxyHandler {

    /** 方法调用 */
    fun onProxyMethodCall(
        call: MethodCallProxy,
        result: ResultProxy,
    )
}
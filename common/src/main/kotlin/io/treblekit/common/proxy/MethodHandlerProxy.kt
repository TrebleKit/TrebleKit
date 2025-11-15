package io.treblekit.common.proxy

/**
 * 代理处理接口
 */
interface MethodHandlerProxy {

    /** 方法调用 */
    fun onProxyMethodCall(
        call: MethodCallProxy,
        result: MethodResultProxy,
    )
}
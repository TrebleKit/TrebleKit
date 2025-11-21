package io.treblekit.common.proxy

/**
 * 方法调用代理
 */
interface MethodCallProxy {

    /** 方法名代理 */
    val methodProxy: String

    /** 参数代理 */
    fun <T> argumentProxy(key: String): T?
}

package io.treblekit.common

/**
 * 返回内容代理
 */
interface ResultProxy {

    /**
     * 处理成功结果.
     * @param resultProxy 处理成功结果,注意可能为空.
     */
    fun success(resultProxy: Any?)

    /**
     * 处理错误结果.
     * @param errorCodeProxy 错误代码.
     * @param errorMessageProxy 错误消息,注意可能为空.
     * @param errorDetailsProxy 详细信息,注意可能为空.
     */
    fun error(
        errorCodeProxy: String,
        errorMessageProxy: String?,
        errorDetailsProxy: Any?,
    )

    /**
     * 处理对未实现方法的调用.
     */
    fun notImplemented()
}
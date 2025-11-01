package io.treblekit.engine

/**
 * 扩展函数判断是否为空
 */
inline val Any?.isNull: Boolean
    get() = this@isNull == null

/**
 * 扩展函数判断是否为空
 */
inline val Any?.isNotNull: Boolean
    get() = this@isNotNull != null
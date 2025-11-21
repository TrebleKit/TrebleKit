package io.treblekit.utils

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatformTools

/**
 * 定义实例
 *
 * @param name 实例名称
 * @param instance 实例
 * @return KoinDefinition<T>
 */
inline fun <reified T : Any> Module.instance(
    name: String? = null,
    instance: T? = null,
): KoinDefinition<T> {
    return single<T>(
        qualifier = named(
            name = name ?: error(
                message = "name is null.",
            )
        ),
        createdAtStart = true,
        definition = {
            instance ?: error(
                message = "instance is null.",
            )
        },
    )
}

/**
 * 注入实例
 *
 * @param name 实例名称
 * @return Lazy<T>
 */
inline fun <reified T : Any> KoinComponent.injectInstance(
    name: String? = null,
): Lazy<T> {
    return lazy(
        mode = KoinPlatformTools.defaultLazyMode()
    ) {
        return@lazy get<T>(
            qualifier = named(
                name = name ?: error(
                    message = "name is null.",
                ),
            )
        )
    }
}
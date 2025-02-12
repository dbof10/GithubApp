package com.github.arch

import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

object ReduxDuration {
    const val DURATION_SHORT = 200L // in milliseconds
    const val DURATION_MEDIUM = 500L
    const val DURATION_LONG = 1000L // For debouncing or throttling events from the UI, like click, tap, swipe,..
}

/**
This extension helps filter the type of item from the upstream flow.
It only allows the emission of items of type 'SubA' to the downstream flow.
 */
inline fun <A : Any, reified SubA : A> Flow<A>.ofType(clz: KClass<SubA>): Flow<SubA> =
    this.filter { it is SubA }.map { it as SubA }

/**
This extension helps prevent the emission of the same consecutive value within the periodMillis timeframe,
but it still allows the emission of consecutive different values within the periodMillis timeframe.
 */
fun <T> Flow<T>.throttleDistinct(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "periodMillis should be positive number" }
    return flow {
        var lastTime = 0L
        var lastValue: Any? = null
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                lastValue = value
                emit(value)
            } else {
                // within the timeframe, but different value
                if (lastValue != value) {
                    lastTime = currentTime
                    lastValue = value
                    emit(value)
                }
            }
        }
    }
}

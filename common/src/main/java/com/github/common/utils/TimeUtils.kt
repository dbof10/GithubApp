package com.github.common.utils

interface TimeUtils {
    fun currentMillis(): Long
}

class TimeUtilsImpl : TimeUtils {
    override fun currentMillis(): Long {
        return System.currentTimeMillis()
    }

}

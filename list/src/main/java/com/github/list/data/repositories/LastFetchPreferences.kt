package com.github.list.data.repositories

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import javax.inject.Inject

class LastFetchPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
       @VisibleForTesting internal const val KEY_LAST_FETCH = "last_time_fetch"
    }

    fun saveLastFetchTime(timestamp: Long) {
        sharedPreferences.edit().putLong(KEY_LAST_FETCH, timestamp).apply()
    }

    fun getLastFetchTime(): Long {
        return sharedPreferences.getLong(KEY_LAST_FETCH, 0L)
    }
}

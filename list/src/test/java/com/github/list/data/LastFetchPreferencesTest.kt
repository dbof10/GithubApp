package com.github.list.data

import android.content.SharedPreferences
import com.github.list.data.repositories.LastFetchPreferences
import com.github.list.data.repositories.LastFetchPreferences.Companion.KEY_LAST_FETCH
import com.github.test.MockkUnitTest
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Test

class LastFetchPreferencesTest : MockkUnitTest() {

    @RelaxedMockK
    private lateinit var sharedPreferences: SharedPreferences
    @RelaxedMockK
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var lastFetchPreferences: LastFetchPreferences

    override fun setUp() {

        every { sharedPreferences.edit() } returns editor
        every { editor.putLong(any(), any()) } returns editor

        lastFetchPreferences = LastFetchPreferences(sharedPreferences)
    }

    @Test
    fun `when getLastFetchTime is called, it should return saved timestamp`() {
        every { sharedPreferences.getLong(KEY_LAST_FETCH, 0L) } returns 1700000000000L

        val result = lastFetchPreferences.getLastFetchTime()

        assertEquals(1700000000000L, result)
    }

}

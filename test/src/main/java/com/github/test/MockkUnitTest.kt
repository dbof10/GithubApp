package com.github.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class MockkUnitTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    open fun setUp() {
        // let children unit test classes override if needed
    }

    open fun tearDown() {
        // let children unit test classes override if needed
    }

    @Before
    fun init() {
        MockKAnnotations.init(this)
        setUp()
    }

    @After
    fun destroy() {
        tearDown()
        unmockkAll()
        clearAllMocks()
    }
}

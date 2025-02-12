package com.github.list.presentation


import com.github.list.presentation.redux.ListStateMachine
import com.github.test.MockkUnitTest
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ListViewModelTest : MockkUnitTest() {

    @RelaxedMockK
    lateinit var machine: ListStateMachine

    @RelaxedMockK
    lateinit var navigator: ListNavigator

    private lateinit var viewModel: ListViewModel

    @Before
    override fun setUp() {
        viewModel = ListViewModel(machine)
        viewModel.setNavigator(navigator)
    }

    @Test
    fun `when OpenDetail navigation is triggered, should navigate to detail screen`() {
        // Given
        val username = "john_doe"
        val navigation = ListResultsNavigation.OpenDetail(username)

        // When
        viewModel.handleNavigation(navigation)

        // Then
        verify { navigator.navigateToDetail(username) }
    }
}

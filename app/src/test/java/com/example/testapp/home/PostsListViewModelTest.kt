package com.example.testapp.home

import com.example.testapp.domain.faverites_post.AddFavoritePostUseCase
import com.example.testapp.domain.faverites_post.LoadFavoritePostUseCase
import com.example.testapp.domain.faverites_post.RemoveFavoritePostUseCase
import com.example.testapp.domain.posts.LoadAllPostsUseCase
import com.example.testapp.home.testdoubles.ScenarioType
import com.example.testapp.home.testdoubles.getPosts
import com.example.testapp.presentation.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostsListViewModelTest {

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    private val loadAllPostsItem = mockk<LoadAllPostsUseCase>()
    private val addFavoritePostUseCase = mockk<AddFavoritePostUseCase>()
    private val removeFavoritePostUseCase = mockk<RemoveFavoritePostUseCase>()
    private val loadFavoritePostUseCase = mockk<LoadFavoritePostUseCase>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            loadAllPostsItem,
            addFavoritePostUseCase,
            removeFavoritePostUseCase, loadFavoritePostUseCase
        )
    }

    @Test
    fun `When data is empty`() = runTest {
        coEvery { loadAllPostsItem(Unit) } returns Result.success(getPosts(ScenarioType.EMPTY))

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as HomeViewModel.UiState.Success
        assertTrue(value.postsItem.isEmpty())
    }

    @Test
    fun `When data loads with no duplicates, verify the data`() = runTest {
        coEvery { loadAllPostsItem(Unit) } returns Result.success(getPosts())

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as HomeViewModel.UiState.Success
        assertEquals(3, value.postsItem.size)
    }

    @Test
    fun `When data loads with duplicates, verify the data`() = runTest {
        val data = getPosts(ScenarioType.DUPLICATES)
        coEvery { loadAllPostsItem(Unit) } returns Result.success(data)

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as HomeViewModel.UiState.Success
        assertEquals(4, value.postsItem.size)
        assertEquals(data[0].title, value.postsItem[0].title)
        assertEquals(data[3].title, value.postsItem[3].body)
    }

    @Test
    fun `When data loads as unsorted list, verify the sorted list`() = runTest {
        val data = getPosts(ScenarioType.UNSORTED)
        coEvery { loadAllPostsItem(Unit) } returns Result.success(data)

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as HomeViewModel.UiState.Success
        assertEquals(3, value.postsItem.size)
        assertEquals(data[0].title, value.postsItem[2].body)
        assertEquals(data[1].title, value.postsItem[1].body)
        assertEquals(data[2].title, value.postsItem[0].body)
    }

    @Test
    fun `When repo fails with unknown reason, then handle and show unknown error message`() =
        runTest {
            val exception = Exception()
            coEvery { loadAllPostsItem(Unit) } returns Result.failure(exception)

            viewModel.loadData()
            advanceUntilIdle()

            val value = viewModel.uiState.value as HomeViewModel.UiState.Error
            assertEquals(DEFAULT_ERROR_MSG, value.msg)
        }

    @Test
    fun `When repo fails with custom error, then show that error message`() = runTest {
        coEvery { loadAllPostsItem(Unit) } returns Result.failure(Throwable(ERROR_MSG))

        viewModel.loadData()
        advanceUntilIdle()

        val value = viewModel.uiState.value as HomeViewModel.UiState.Error
        assertEquals(ERROR_MSG, value.msg)
    }

    private companion object {
        const val DEFAULT_ERROR_MSG = "Unknown Error"
        const val ERROR_MSG = "Error"
    }
}

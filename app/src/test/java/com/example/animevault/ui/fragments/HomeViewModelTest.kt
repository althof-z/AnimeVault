package com.example.animevault.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.animevault.MainDispatcherRule
import com.example.animevault.ui.fragments.feature.HomeFragmentViewModel
import com.example.domain.model.AnimeHome
import com.example.domain.repository.AnimeRepository
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.RuntimeException

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val animeRepository = mock<AnimeRepository>()
    private val authRepository = mock<AuthRepository>()

    private lateinit var viewModel: HomeFragmentViewModel

    private val loadingObserver = mock<Observer<Boolean>>()
    private val successObserver = mock<Observer<List<AnimeHome>>>()
    private val errorObserver = mock<Observer<Throwable>>()

    @Before
    fun setup() {
        viewModel = HomeFragmentViewModel(animeRepository, authRepository)
        viewModel.loading.observeForever(loadingObserver)
        viewModel.animes.observeForever(successObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @Test
    fun `retrieveAvailableAnimes success`() = runTest {
        // Given
        val mockAnimeList = listOf(
            AnimeHome(id = 1, image = "http://example.com/image1.jpg", title = "Anime 1", desc = "Description 1"),
            AnimeHome(id = 2, image = "http://example.com/image2.jpg", title = "Anime 2", desc = "Description 2")
        )
        whenever(animeRepository.fetchData()).thenReturn(mockAnimeList)

        // When
        viewModel.retrieveAvailableAnimes()

        // Then
        verify(loadingObserver).onChanged(true)
        verify(successObserver).onChanged(mockAnimeList)
        verify(loadingObserver).onChanged(false)

        assertEquals(mockAnimeList, viewModel.animes.value)
        assertEquals(false, viewModel.loading.value)
    }

    @Test
    fun `retrieveAvailableAnimes failure`() = runTest {
        // Given
        val mockError = RuntimeException("Error fetching data")
        whenever(animeRepository.fetchData()).thenThrow(mockError)

        // When
        viewModel.retrieveAvailableAnimes()

        // Then
        verify(loadingObserver).onChanged(true)
        verify(errorObserver).onChanged(mockError)
        verify(loadingObserver).onChanged(false)

        assertEquals(mockError, viewModel.error.value)
        assertEquals(false, viewModel.loading.value)
    }

    @Test
    fun `logout success`() = runTest {
        // When
        viewModel.logout()

        // Then
        verify(authRepository).clearToken()
    }

    @Test
    fun `logout failure`() = runTest {
        // Given
        val mockError = RuntimeException("Error during logout")
        whenever(authRepository.clearToken()).thenThrow(mockError)

        // When
        viewModel.logout()

        // Then
        verify(errorObserver).onChanged(mockError)

        assertEquals(mockError, viewModel.error.value)
    }
}

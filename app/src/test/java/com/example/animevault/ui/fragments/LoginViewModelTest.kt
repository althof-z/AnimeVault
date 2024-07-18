package com.example.animevault.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.animevault.MainDispatcherRule
import com.example.animevault.ui.fragments.auth.LoginViewModel
import com.example.domain.usecases.LoginUsesCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    // Given
    private val loginUseCase = mock<LoginUsesCase>()
    private val viewModel = LoginViewModel(
       loginUsesCase = loginUseCase
    )

    private val successObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<Throwable>>()

    private val successCaptor = argumentCaptor<Boolean>()
    private val errorCaptor = argumentCaptor<Throwable>()

    @Test
    fun loginSuccess() = runTest {
        // given
        val username = "username"
        val password = "password"

        val successLiveData = viewModel.success

        successLiveData.observeForever(successObserver)

        // when
        whenever(loginUseCase.login(username, password)).thenReturn(Unit)
        viewModel.login(username, password)

        // verify
        verify(successObserver).onChanged(successCaptor.capture())
        Assert.assertEquals(successCaptor.allValues, listOf(true))
    }

    @Test
    fun loginErrorGeneral() = runTest {
        // given
        val username = "username"
        val password = "password"

        val successLiveData = viewModel.success
        val errorLiveData = viewModel.error

        successLiveData.observeForever(successObserver)
        errorLiveData.observeForever(errorObserver)

        // when
        val throwable = UnsupportedOperationException("error")
        whenever(loginUseCase.login(username, password)).thenThrow(throwable)
        viewModel.login(username, password)

        // verify
        verify(errorObserver).onChanged(errorCaptor.capture())
        Assert.assertEquals(errorCaptor.allValues, listOf(throwable))
    }
}
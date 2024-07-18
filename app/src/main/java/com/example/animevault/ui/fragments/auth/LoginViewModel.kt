package com.example.animevault.ui.fragments.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.LoginUsesCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUsesCase: LoginUsesCase
) : ViewModel() {


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                loginUsesCase.login(username, password)
                _success.value = true
            } catch (throwable: Throwable) {
                _error.value = throwable
            }
        }
    }
}
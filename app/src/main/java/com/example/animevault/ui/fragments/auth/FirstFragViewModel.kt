package com.example.animevault.ui.fragments.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class FirstFragViewModel ( private val authRepository: AuthRepository
): ViewModel() {

    private val _isAuthed = MutableLiveData<Boolean>()
    val isAuthed: LiveData<Boolean> = _isAuthed

    fun checkLogin() {
        viewModelScope.launch {
            try {
                _isAuthed.value = !authRepository.loadToken().isNullOrEmpty()
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
    }
}
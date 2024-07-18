package com.example.animevault.ui.fragments.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AnimeHome
import com.example.domain.repository.AnimeRepository
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class HomeFragmentViewModel (
    private val animeRepository: AnimeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _animes: MutableLiveData<List<AnimeHome>> = MutableLiveData()
    val animes: LiveData<List<AnimeHome>> = _animes

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun retrieveAvailableAnimes(){
        viewModelScope.launch {
            try{
                _loading.value = true
                _animes.value = animeRepository.fetchData()
                _loading.value = false
            }catch (throwable: Throwable){
                _loading.value = false
                _error.value = throwable
            }

        }

    }

    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.clearToken()
            } catch (throwable: Throwable) {
                _error.value = throwable
            }
        }
    }



}
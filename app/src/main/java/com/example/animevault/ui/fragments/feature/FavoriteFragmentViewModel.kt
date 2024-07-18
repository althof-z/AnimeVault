package com.example.animevault.ui.fragments.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Anime
import com.example.domain.model.AnimeHome
import com.example.domain.repository.AnimeRepository
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(
    private val animeRepository: AnimeRepository,
    private val authRepository: AuthRepository,
): ViewModel(){

    private val _animes: MutableLiveData<List<Anime>> = MutableLiveData()
    val animes: LiveData<List<Anime>> = _animes

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun getMovieFromLocal(){
        viewModelScope.launch {
            try {
                _animes.value = animeRepository.getAllAnime()
            } catch (throwable: Throwable) {
                _error.value = throwable
            }
        }
    }

    fun deleteAnimeFromFavorite(anime: Anime){
        viewModelScope.launch {
            animeRepository.deleteAnime(anime)
        }
    }

    private val _animeLocal = MutableLiveData<Anime?>()
    fun loadAnimeFromFavorite(id: Int){
        viewModelScope.launch {
            try {
                _animeLocal.value = animeRepository.getMovieById(id)
            } catch (throwable: Throwable){
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
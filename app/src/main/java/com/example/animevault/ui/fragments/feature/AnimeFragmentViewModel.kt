package com.example.animevault.ui.fragments.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Anime
import com.example.domain.repository.AnimeRepository
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class AnimeFragmentViewModel(
    private val animeRepository: AnimeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _animes: MutableLiveData<List<Anime>> = MutableLiveData()
    val animes: LiveData<List<Anime>> = _animes

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun retrieveAvailableAnimes(){
        viewModelScope.launch {
            try{
                _loading.value = true
                _animes.value = animeRepository.fetchDataPlus()
                _loading.value = false
            }catch (throwable: Throwable){
                _loading.value = false
                _error.value = throwable
            }

        }

    }

    fun storeToFavorite(
        id: Int,
        image: String,
        title: String,
        synopsis: String,
        year: Int,
        episode: String,
        rate: Double
    ){
      viewModelScope.launch {
          val anime = Anime(
              id = id,
              image = image,
              rate = rate,
              episode = episode ,
              year = year,
              title = title,
              synopsis = synopsis,
          )
          animeRepository.storeFavorite(anime)
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


}
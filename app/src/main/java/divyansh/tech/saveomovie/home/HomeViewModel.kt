package divyansh.tech.saveomovie.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.saveomovie.home.dataModels.HomeScreenModel
import divyansh.tech.saveomovie.home.dataModels.MovieList
import divyansh.tech.saveomovie.home.source.HomeRemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRemoteRepo
): ViewModel() {

    private val _moviesLiveData = MutableLiveData<HomeScreenModel>()
    val moviesLiveData get() = _moviesLiveData

    private var nowShowingPage = 1


    init {
        getMovies()
    }

    fun getMovies() = viewModelScope.launch(Dispatchers.IO) {
        val response  = repo.getMovies(nowShowingPage)
        response?.let {
            _moviesLiveData.postValue(it)
        }
    }
}
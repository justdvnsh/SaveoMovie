package divyansh.tech.saveomovie.home

import android.util.Log
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

    private var nowShowingPage = 2

    private var homeScreenModel = HomeScreenModel()

    init {
        getMovies()
        getNowShowingMovies()
    }

    private fun getMovies() = viewModelScope.launch(Dispatchers.IO) {
        val response  = repo.getMovies()
        response?.let {
            homeScreenModel.popularMovies = it.results
            _moviesLiveData.postValue(homeScreenModel)
        }
    }

    private fun getNowShowingMovies() = viewModelScope.launch(Dispatchers.IO) {
        val response  = repo.getNowShowing(1)
        response?.let {
            homeScreenModel.nowShowing = it.results
            _moviesLiveData.postValue(homeScreenModel)
        }
    }

    fun getNowShowingByPage() = viewModelScope.launch(Dispatchers.IO) {
        val response  = repo.getNowShowing(nowShowingPage++)
        response?.let {
            homeScreenModel.nowShowing.addAll(it.results)
            val model = HomeScreenModel(
                popularMovies = homeScreenModel.popularMovies,
                nowShowing = homeScreenModel.nowShowing
            )
            homeScreenModel = model
            _moviesLiveData.postValue(model)
        }
    }
}
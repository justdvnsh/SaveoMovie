package divyansh.tech.saveomovie.movieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.saveomovie.movieDetails.dataModels.MovieDetail
import divyansh.tech.saveomovie.movieDetails.source.MovieDetailRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repo: MovieDetailRepo
): ViewModel() {

    private val _movieDetailLiveData = MutableLiveData<MovieDetail>()
    val movieDetailLiveData get() = _movieDetailLiveData

    fun getMovieDetail(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.getMovieDetails(id)
        response?.let {
            _movieDetailLiveData.postValue(it)
        }
    }
}
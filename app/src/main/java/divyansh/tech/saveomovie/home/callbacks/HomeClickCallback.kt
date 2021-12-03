package divyansh.tech.saveomovie.home.callbacks

import androidx.appcompat.widget.AppCompatImageView
import divyansh.tech.saveomovie.home.HomeViewModel
import divyansh.tech.saveomovie.home.dataModels.Movie

class HomeClickCallback(
    private val viewModel: HomeViewModel
) {

    fun onMovieClick(imageView: AppCompatImageView, movie: Movie) {
        viewModel.navigate(imageView, movie)
    }
}
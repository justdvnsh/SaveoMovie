package divyansh.tech.saveomovie.home.dataModels

import androidx.appcompat.widget.AppCompatImageView

data class HomeScreenModel(
    var popularMovies: ArrayList<Movie> = ArrayList(),
    var nowShowing: ArrayList<Movie> = ArrayList()
)

data class ShareModel(
    val imageView: AppCompatImageView,
    val movie: Movie
)
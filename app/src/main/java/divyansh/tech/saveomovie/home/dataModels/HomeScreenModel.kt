package divyansh.tech.saveomovie.home.dataModels

data class HomeScreenModel(
    var popularMovies: ArrayList<Movie> = ArrayList(),
    var nowShowing: ArrayList<Movie> = ArrayList()
)
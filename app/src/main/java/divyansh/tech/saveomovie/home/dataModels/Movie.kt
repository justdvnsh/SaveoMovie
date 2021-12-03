package divyansh.tech.saveomovie.home.dataModels

import java.io.Serializable

data class MovieList(
    val results: ArrayList<Movie>
)

data class Movie(
    val id: Int,
    val poster_path: String
): Serializable
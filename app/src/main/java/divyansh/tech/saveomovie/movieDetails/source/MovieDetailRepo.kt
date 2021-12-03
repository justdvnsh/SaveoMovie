package divyansh.tech.saveomovie.movieDetails.source

import divyansh.tech.saveomovie.movieDetails.dataModels.MovieDetail
import divyansh.tech.saveomovie.utils.C.API_KEY
import divyansh.tech.saveomovie.utils.C.LANGUAGE
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class MovieDetailRepo @Inject constructor(
    retrofit: Retrofit
){

    private val service = retrofit.create(MovieDetailInterface::class.java)

    suspend fun getMovieDetails(id: Int): MovieDetail? {
        val response = service.getMovieDetails(id = id)
        return if (response.body() == null) null
        else response.body()
    }

    interface MovieDetailInterface {
        @GET("movie/{id}")
        suspend fun getMovieDetails(
            @Path("id") id: Int,
            @Query("api_key") apikey: String = API_KEY,
            @Query("language") lang: String = LANGUAGE
        ): Response<MovieDetail>
    }
}
package divyansh.tech.saveomovie.home.source

import divyansh.tech.saveomovie.home.dataModels.HomeScreenModel
import divyansh.tech.saveomovie.home.dataModels.Movie
import divyansh.tech.saveomovie.home.dataModels.MovieList
import divyansh.tech.saveomovie.utils.C.API_KEY
import divyansh.tech.saveomovie.utils.C.LANGUAGE
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class HomeRemoteRepo @Inject constructor(
    retrofit: Retrofit
){

    private val service = retrofit.create(HomeService::class.java)

    suspend fun getMovies(page: Int): HomeScreenModel? {
        val responsePopular = service.getPopularMovies()
        val responseNowShowing = service.getNowPlaying(page = page)
        return if (responsePopular.body() == null || responseNowShowing.body() == null) null
        else HomeScreenModel(
            popularMovies = responsePopular.body()!!.results,
            nowShowing = responseNowShowing.body()!!.results
        )
    }

    interface HomeService {

        @GET("trending/all/day")
        suspend fun getPopularMovies(
            @Query("api_key") key: String = API_KEY
        ): Response<MovieList>

        @GET("movie/now_playing")
        suspend fun getNowPlaying(
            @Query("api_key") key: String = API_KEY,
            @Query("language") lang: String = LANGUAGE,
            @Query("page") page: Int
        ): Response<MovieList>

    }
}
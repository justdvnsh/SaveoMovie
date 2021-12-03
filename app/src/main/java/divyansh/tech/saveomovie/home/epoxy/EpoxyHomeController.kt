package divyansh.tech.saveomovie.home.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.saveomovie.home.callbacks.HomeClickCallback
import divyansh.tech.saveomovie.home.dataModels.HomeScreenModel

class EpoxyHomeController(
    private val homeCallback: HomeClickCallback
): TypedEpoxyController<HomeScreenModel>() {
    override fun buildModels(data: HomeScreenModel?) {
        data?.let {
            val list = ArrayList<EpoxyPopularMovieModel_>()
            it.popularMovies.forEach {
                list.add(EpoxyPopularMovieModel_()
                    .id(it.hashCode())
                    .movie(it)
                    .spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                )
            }

            CarouselModel_()
                .id("POPULAR_MOVIES")
                .models(list)
                .padding(Carousel.Padding.dp(30,0,30,0,20))
                .addTo(this)

            epoxyTitle {
                id("EPOXY TITLE")
                title("Now Showing")
                spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount}
            }

            it.nowShowing.forEach {
                epoxyNowShowing {
                    id(it.hashCode())
                    movie(it)
                    callback(homeCallback)
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount / 3}
                }
            }
        }
    }
}
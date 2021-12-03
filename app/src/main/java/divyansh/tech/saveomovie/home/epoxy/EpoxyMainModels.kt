package divyansh.tech.saveomovie.home.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.saveomovie.BR
import divyansh.tech.saveomovie.R
import divyansh.tech.saveomovie.home.dataModels.Movie

@EpoxyModelClass(layout = R.layout.recycler_popular_movies_item)
abstract class EpoxyPopularMovieModel(): DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var movie: Movie

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.movie, movie)
    }
}

@EpoxyModelClass(layout = R.layout.recycler_now_showing_item)
abstract class EpoxyNowShowingModel(): DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var movie: Movie

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.movie, movie)
    }
}

@EpoxyModelClass(layout = R.layout.recycler_title_item)
abstract class EpoxyTitleModel(): DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var title: String

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.title, title)
    }
}


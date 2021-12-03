package divyansh.tech.saveomovie.home.epoxy

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.*
import com.bumptech.glide.Glide
import divyansh.tech.saveomovie.BR
import divyansh.tech.saveomovie.R
import divyansh.tech.saveomovie.home.callbacks.HomeClickCallback
import divyansh.tech.saveomovie.home.dataModels.Movie
import divyansh.tech.saveomovie.utils.C.IMAGE_BASE_URL

@EpoxyModelClass(layout = R.layout.recycler_popular_movies_item)
abstract class EpoxyPopularMovieModel(): DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var movie: Movie

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.movie, movie)
    }
}

@EpoxyModelClass(layout = R.layout.recycler_now_showing_item)
abstract class EpoxyNowShowingModel(): EpoxyModelWithHolder<EpoxyNowShowingModel.Holder>() {

    @EpoxyAttribute
    lateinit var movie: Movie

    @EpoxyAttribute
    lateinit var callback: HomeClickCallback

    override fun bind(holder: Holder) {
        super.bind(holder)
        ViewCompat.setTransitionName(holder.imageView, "image-${movie.id}")
        Glide
            .with(holder.imageView.context)
            .load(IMAGE_BASE_URL + movie.poster_path)
            .into(holder.imageView)
        holder.imageView.setOnClickListener {
            callback.onMovieClick(it as AppCompatImageView, movie)
        }
    }


    class Holder(): EpoxyHolder() {
        lateinit var imageView: AppCompatImageView

        override fun bindView(itemView: View) {
            imageView = itemView.findViewById(R.id.movieImage)
        }

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


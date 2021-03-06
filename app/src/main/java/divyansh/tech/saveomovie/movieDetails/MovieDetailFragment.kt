package divyansh.tech.saveomovie.movieDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.saveomovie.common.animateView
import divyansh.tech.saveomovie.databinding.FragmentMovieDetailBinding
import divyansh.tech.saveomovie.utils.C.IMAGE_BASE_URL


@AndroidEntryPoint
class MovieDetailFragment: Fragment() {

    private lateinit var _binding: FragmentMovieDetailBinding

    private val viewModel by viewModels<MovieDetailViewModel>()

    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        super.onViewCreated(view, savedInstanceState)
        animate()
        setupObservers()
        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovieDetail(args.movie.id)
    }

    private fun setupObservers() {
        viewModel.movieDetailLiveData.observe(
            viewLifecycleOwner,
            Observer {
                _binding.movie = it
                animateViews()
            }
        )
    }

    private fun setOnClickListeners() {
        _binding.hamburger.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun animate() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        Glide.with(this)
            .load(IMAGE_BASE_URL + args.movie.poster_path)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .dontTransform()
            .override(requireView().width, requireView().height)
            .into(_binding.imageView)
    }

    private fun animateViews() {
        _binding.overview.animateView()
        _binding.name.animateView()
        _binding.ratings.animateView()
    }
}
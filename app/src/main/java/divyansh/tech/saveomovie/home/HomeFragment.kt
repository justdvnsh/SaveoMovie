package divyansh.tech.saveomovie.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.saveomovie.R
import divyansh.tech.saveomovie.common.EventObserver
import divyansh.tech.saveomovie.databinding.FragmentHomeBinding
import divyansh.tech.saveomovie.home.callbacks.HomeClickCallback
import divyansh.tech.saveomovie.home.epoxy.EpoxyHomeController
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    private val controller by lazy {
        EpoxyHomeController(HomeClickCallback(viewModel))
    }

//    private val

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setupRecyclerView() {
        _binding.homeRV.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            controller.spanCount = 3
            (layoutManager as GridLayoutManager).spanSizeLookup = controller.spanSizeLookup
            adapter = controller.adapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    (this@apply as RecyclerView).layoutManager?.let {
                        val carousel = it.findViewByPosition(1)
                        if (carousel != null) _binding.toolbar.title.text  = getString(R.string.movies)
                        else _binding.toolbar.title.text = getString(R.string.now_showing)
                        if (dy > 0) {
                            val visibleItemCount = (it as GridLayoutManager).childCount
                            val totalItemCount = (it as GridLayoutManager).itemCount
                            val pastVisibleItem = (it as GridLayoutManager).findFirstVisibleItemPosition()
                            if (visibleItemCount + pastVisibleItem >= totalItemCount && pastVisibleItem >= 2) {
                                viewModel.getNowShowingByPage()
                            }

                        }
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.moviesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                controller.setData(it)
            }
        )

        viewModel.navigateLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                val extra = FragmentNavigatorExtras(
                    it.imageView to "imageView"
                )
                val bundle = Bundle().apply {
                    putSerializable("movie", it.movie)
                }
                findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle, null, extra)
            }
        )
    }
}
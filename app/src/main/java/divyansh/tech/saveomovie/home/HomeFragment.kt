package divyansh.tech.saveomovie.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.saveomovie.R
import divyansh.tech.saveomovie.databinding.FragmentHomeBinding
import divyansh.tech.saveomovie.home.epoxy.EpoxyHomeController
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    private val controller by lazy {
        EpoxyHomeController()
    }

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
                    if (dy > 0) _binding.toolbar.title.text  = getString(R.string.now_showing)
                    else _binding.toolbar.title.text = getString(R.string.movies)
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
    }
}
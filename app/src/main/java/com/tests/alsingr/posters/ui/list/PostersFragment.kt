package com.tests.alsingr.posters.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tests.alsingr.posters.adapters.PosterAdapter
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.domain.Resource
import com.tests.alsingr.posters.data.domain.Status
import com.tests.alsingr.posters.databinding.FragPostersBinding
import com.tests.alsingr.posters.utilities.InjectorUtils
import com.tests.alsingr.posters.viewmodels.PosterListViewModel
import timber.log.Timber


class PostersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragPostersBinding.inflate(inflater, container, false)
        val adapter = PosterAdapter()
        binding.postersList.adapter = adapter
        subscribeUI(adapter, binding)
        return binding.root
    }

    private fun subscribeUI(adapter: PosterAdapter, binding: FragPostersBinding) {
        val factory = InjectorUtils.providePostersViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory)
            .get(PosterListViewModel::class.java)
        viewModel.posters.observe(viewLifecycleOwner, Observer { posters ->
            when (posters.status) {
                Status.SUCCESS -> displayPostersIfNeeded(binding, posters.data, adapter)
                Status.LOADING -> displayLoadingMessage(binding)
                Status.ERROR -> displayErrorMessage(binding)
            }
        })
    }

    private fun displayPostersIfNeeded(binding: FragPostersBinding, data: List<Poster>?, adapter: PosterAdapter) {
        binding.isFinishedWithError = false
        binding.isLoading = false
        if (data != null && data.isNotEmpty()) {
            binding.hasPosters = true
            adapter.submitList(data)
        }else {
            // display no posters message
            binding.hasPosters = false
        }
    }

    private fun displayErrorMessage(binding: FragPostersBinding) {
        binding.isFinishedWithError = true
        binding.isLoading = false
        binding.hasPosters = true
    }

    private fun displayLoadingMessage(binding: FragPostersBinding) {
        binding.isLoading = true
        binding.isFinishedWithError = false
        binding.hasPosters = true
    }

}

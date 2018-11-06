package com.tests.alsingr.posters.ui.list

import android.os.Bundle
import android.util.Log
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
import com.tests.alsingr.posters.ui.shared.RetryCallback
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

        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.retry()
            }
        }

        viewModel.posters.observe(viewLifecycleOwner, Observer { posters ->
            binding.postersResource = posters
            if (posters.data != null && posters.data.isNotEmpty()) {
                binding.hasPosters = true
                adapter.submitList(posters.data)
            }else {
                // display no posters message
                binding.hasPosters = false
            }
        })
        viewModel.retry()
    }
}

package com.tests.alsingr.posters.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tests.alsingr.posters.data.PostersRepository


/**
 * Factory for creating a [PosterListViewModel] with a constructor that takes a [PosterRepository].
 */
class PosterListViewModelFactory(
    private val repository: PostersRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = PosterListViewModel(repository) as T
}

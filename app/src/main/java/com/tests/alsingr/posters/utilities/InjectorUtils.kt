package com.tests.alsingr.posters.utilities

import android.content.Context
import com.tests.alsingr.posters.data.PostersRepository
import com.tests.alsingr.posters.data.source.local.AppDatabase
import com.tests.alsingr.posters.data.source.remote.PosterService
import com.tests.alsingr.posters.viewmodels.PosterListViewModelFactory


/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getPostersRepository(context: Context): PostersRepository {
        return PostersRepository.getInstance(AppExecutors.getInstance(), AppDatabase.getInstance(context).posterDAO(), PosterService.getInstance())
    }

    fun providePostersViewModelFactory(context: Context): PosterListViewModelFactory {
        val repository = getPostersRepository(context)
        return PosterListViewModelFactory(repository)
    }

}

package com.tests.alsingr.posters.data

import androidx.lifecycle.LiveData
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.domain.Resource
import com.tests.alsingr.posters.data.source.local.AppDatabase
import com.tests.alsingr.posters.data.source.local.PosterDataAccessObject
import com.tests.alsingr.posters.data.source.remote.PosterService
import com.tests.alsingr.posters.utilities.AppExecutors
import timber.log.Timber
import javax.inject.Inject

class PostersRepository (
    private val appExecutors: AppExecutors,
    private val posterDao: PosterDataAccessObject,
    private val postersService: PosterService
) {

    fun fetchPosters(): LiveData<Resource<List<Poster>>> {
        return object : NetworkBoundResource<List<Poster>, List<Poster>>(appExecutors) {
            override fun saveCallResult(item: List<Poster>) {
                posterDao.insertAll(item)
            }

            override fun shouldFetch(data: List<Poster>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = posterDao.fetchPosters()

            override fun createCall() = postersService.getPosters()

            override fun onFetchFailed() {
                Timber.w("Failed")
            }
        }.asLiveData()
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PostersRepository? = null

        fun getInstance(appExecutors: AppExecutors,
                        posterDao: PosterDataAccessObject,
                        postersService: PosterService) =
            instance ?: synchronized(this) {
                instance ?: PostersRepository(appExecutors, posterDao, postersService).also { instance = it }
            }
    }
}
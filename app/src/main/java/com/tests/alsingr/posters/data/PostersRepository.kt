package com.tests.alsingr.posters.data

import androidx.lifecycle.LiveData
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.domain.Resource
import com.tests.alsingr.posters.data.source.local.AppDatabase
import com.tests.alsingr.posters.data.source.local.PosterDataAccessObject
import com.tests.alsingr.posters.data.source.remote.PosterService
import com.tests.alsingr.posters.utilities.AppExecutors
import javax.inject.Inject

class PostersRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: AppDatabase,
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
            }
        }.asLiveData()
    }
}
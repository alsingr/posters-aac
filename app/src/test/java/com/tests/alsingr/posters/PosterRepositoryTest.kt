package com.tests.alsingr.posters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tests.alsingr.posters.data.PostersRepository
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.domain.Resource
import com.tests.alsingr.posters.data.source.local.AppDatabase
import com.tests.alsingr.posters.data.source.local.PosterDataAccessObject
import com.tests.alsingr.posters.data.source.remote.PosterService
import com.tests.alsingr.posters.utilities.ApiUtil.successCall
import com.tests.alsingr.posters.utilities.InstantAppExecutors
import com.tests.alsingr.posters.utilities.testPosters
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyList
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import retrofit2.Response

@RunWith(JUnit4::class)
class RepoRepositoryTest {
    private lateinit var repository: PostersRepository
    private val dao = mock(PosterDataAccessObject::class.java)
    private val service = mock(PosterService::class.java)
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.posterDAO()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = PostersRepository(InstantAppExecutors(), db, dao, service)
    }

    @Test
    fun loadPostersFromNetwork() {
        val dbData = MutableLiveData<List<Poster>>()
        `when`(dao.fetchPosters()).thenReturn(dbData)

        val posters = listOf(testPosters[0])
        val call = successCall(posters)
        `when`(service.getPosters()).thenReturn(call)

        val data = repository.fetchPosters()
        verify(dao).fetchPosters()
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Poster>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))
        val updatedDbData = MutableLiveData<List<Poster>>()
        `when`(dao.fetchPosters()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).getPosters()
        verify(dao).insertAll(posters)

        updatedDbData.postValue(posters)
        verify(observer).onChanged(Resource.success(posters))
    }

    @Test
    fun dontGoToNetworkForFetchigPosters() {
        val dbData = MutableLiveData<List<Poster>>()
        val posters = listOf(testPosters[0], testPosters[1])
        dbData.value = posters
        `when`(dao!!.fetchPosters()).thenReturn(dbData)
        val observer = mock<Observer<Resource<List<Poster>>>>()
        repository.fetchPosters().observeForever(observer)
        verify(service, never()).getPosters()
        verify(observer).onChanged(Resource.success(posters))
    }


}
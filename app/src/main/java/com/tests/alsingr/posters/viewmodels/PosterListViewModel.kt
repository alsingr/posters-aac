package com.tests.alsingr.posters.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tests.alsingr.posters.data.PostersRepository
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.domain.Resource
import com.tests.alsingr.posters.utilities.AbsentLiveData

class PosterListViewModel internal constructor(
    private val postersRepository: PostersRepository
) : ViewModel() {
    private val numbersOfTries: MutableLiveData<Int> = MutableLiveData()
    val posters: LiveData<Resource<List<Poster>>> = Transformations.switchMap(numbersOfTries) { input ->
        if (input == null) {
            AbsentLiveData.create()
        }else {
             postersRepository.fetchPosters()
        }
    }


    fun tryToFetchPosters(forceDataLoad: Boolean = true) {
        if (forceDataLoad == false && numbersOfTries.value!= null) {
            return
        }
        var tries = numbersOfTries.value ?: 0
        numbersOfTries.value = ++tries
    }
}
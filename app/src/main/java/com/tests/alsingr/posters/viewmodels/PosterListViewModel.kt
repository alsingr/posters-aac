package com.tests.alsingr.posters.viewmodels

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tests.alsingr.posters.data.PostersRepository
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.domain.Resource
import com.tests.alsingr.posters.utilities.AbsentLiveData
import java.text.SimpleDateFormat
import java.util.*

class PosterListViewModel internal constructor(
    private val postersRepository: PostersRepository
) : ViewModel() {
    private val _repoId: MutableLiveData<Int> = MutableLiveData()
    //    private var posters: LiveData<Resource<List<Poster>>> = postersRepository.fetchPosters()
    val posters: LiveData<Resource<List<Poster>>> = Transformations.switchMap(_repoId) { input ->
        if (input == null) {
            AbsentLiveData.create()
        }else {
             postersRepository.fetchPosters()
        }
    }
//    fun fetchPosters() = posters

    fun retry() {
        Log.d("JJJJJ", "JJJ")
        _repoId.value = 1
    }
}

//fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
//    return if (owner.isBlank() || name.isBlank()) {
//        AbsentLiveData.create()
//    } else {
//        f(owner, name)
//    }
//}
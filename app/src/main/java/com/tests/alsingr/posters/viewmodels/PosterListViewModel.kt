package com.tests.alsingr.posters.viewmodels

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tests.alsingr.posters.data.PostersRepository
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.domain.Resource
import java.text.SimpleDateFormat
import java.util.*

class PosterListViewModel internal constructor(
    private val postersRepository: PostersRepository
) : ViewModel() {
    val posters: LiveData<Resource<List<Poster>>> = postersRepository.fetchPosters()

}
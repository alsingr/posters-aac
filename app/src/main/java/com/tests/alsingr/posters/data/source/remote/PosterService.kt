package com.tests.alsingr.posters.data.source.remote

import androidx.lifecycle.LiveData
import com.tests.alsingr.posters.data.domain.Poster
import retrofit2.http.GET


/**
 * REST API access points
 */
interface PosterService {

    @GET("technical-test.json")
    fun getPosters(): LiveData<ApiResponse<List<Poster>>>

}
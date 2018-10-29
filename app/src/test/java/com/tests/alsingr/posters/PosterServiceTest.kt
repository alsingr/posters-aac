package com.tests.alsingr.posters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tests.alsingr.posters.data.source.remote.ApiSuccessResponse
import com.tests.alsingr.posters.data.source.remote.PosterService
import com.tests.alsingr.posters.utilities.LiveDataCallAdapterFactory
import com.tests.alsingr.posters.utilities.getValue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class GithubServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: PosterService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(PosterService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPosters() {
        enqueueResponse("technical-test.json")
        val value = getValue(service.getPosters())
        val posters = (value as ApiSuccessResponse).body
        assertThat(posters.size, `is`(5000))
        val po = posters[0]
        assertThat(po.id, `is`(1))
        assertThat(po.albumId, `is` (1))
        assertThat(po.title, `is`("accusamus beatae ad facilis cum similique qui sunt"))
        assertThat(posters[1].url, `is`("https://via.placeholder.com/600/771796"))
        assertThat(posters[2].url, `is`("https://via.placeholder.com/600/24f355"))
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}
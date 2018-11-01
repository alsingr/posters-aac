package com.tests.alsingr.posters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.data.source.local.AppDatabase
import com.tests.alsingr.posters.data.source.local.PosterDataAccessObject
import com.tests.alsingr.posters.utilities.getValue
import com.tests.alsingr.posters.utilities.testPosters
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.equalTo
import org.junit.*


@RunWith(AndroidJUnit4::class)
class PostersLocalDataSourceTest {
    private lateinit var database: AppDatabase
    private lateinit var posterDAO: PosterDataAccessObject

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        posterDAO = database.posterDAO()

        // Insert posters in non-alphabetical order to test that results are sorted by name
        posterDAO.insertAll(testPosters)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testFetchPosters() {
        val postersList = getValue(posterDAO.fetchPosters())
        Assert.assertThat(postersList.size, equalTo(3))

        Assert.assertTrue(postersList.contains(testPosters[0]))
        Assert.assertTrue(postersList.contains(testPosters[1]))
        Assert.assertTrue(postersList.contains(testPosters[2]))
    }
}

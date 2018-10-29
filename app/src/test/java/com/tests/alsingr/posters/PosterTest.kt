package com.tests.alsingr.posters

import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.utilities.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PosterTest {
    private lateinit var poster: Poster

    @Before
    fun setUp() {
        poster = Poster(RANDOM_POSTER_ALBUMID, RANDOM_POSTER_ID, RANDOM_POSTER_TITLE, RANDOM_POSTER_URL, RANDOM_POSTER_THUMBNAIL_URL)
    }

    @Test
    fun test_default_values() {
        Assert.assertEquals(RANDOM_POSTER_ALBUMID, poster.albumId)
        Assert.assertEquals(RANDOM_POSTER_ID, poster.id)
        Assert.assertEquals(RANDOM_POSTER_TITLE, poster.title)
        Assert.assertEquals(RANDOM_POSTER_URL, poster.url)
        Assert.assertEquals(RANDOM_POSTER_THUMBNAIL_URL, poster.thumbnailUrl)
    }

    @Test fun test_toString() {
        Assert.assertEquals("${RANDOM_POSTER_ALBUMID} ${RANDOM_POSTER_ID} ${RANDOM_POSTER_TITLE}", poster.toString())
    }
}
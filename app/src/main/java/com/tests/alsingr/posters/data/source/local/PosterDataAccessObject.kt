package com.tests.alsingr.posters.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tests.alsingr.posters.data.domain.Poster


/**
 * Data Access Object for posters.
 */

@Dao
interface PosterDataAccessObject {
    /**
     * Select all posters from the posters table.
     *
     * @return all posters.
     */
    @Query("SELECT * FROM posters")
    fun fetchPosters(): LiveData<List<Poster>>


    @Query("SELECT * FROM posters WHERE id = :posterId")
    fun getPoster(posterId: String): LiveData<Poster>

    /**
     * Insert posters in the database.
     *
     * @param poster the poster to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posters: List<Poster>)

}


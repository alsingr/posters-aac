package com.tests.alsingr.posters.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.utilities.DATABASE_NAME
import com.tests.alsingr.posters.utilities.DATABASE_VERSION


/**
 * The Room database for this app
 */
@Database(entities = arrayOf(Poster::class), version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun posterDAO(): PosterDataAccessObject

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create the database.
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
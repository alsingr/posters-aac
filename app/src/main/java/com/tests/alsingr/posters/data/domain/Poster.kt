package com.tests.alsingr.posters.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posters")
data class Poster (@ColumnInfo(name = "albumId")  val albumId: Int,
                   @PrimaryKey @ColumnInfo(name = "id") val id: Int,
                   @ColumnInfo(name = "title") val title: String,
                   @ColumnInfo(name = "url") val url: String,
                   @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String){
}
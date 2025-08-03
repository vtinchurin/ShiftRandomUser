package ru.vtinchurin.shiftrandomuser.core.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageCache(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "small")
    val small: String,
    @ColumnInfo(name = "large")
    val large: String,
)
package ru.vtinchurin.shiftrandomuser.core.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserCache(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "image_id")
    val imageId: Long = -1,
    @ColumnInfo("country")
    val country: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo("longitude")
    val longitude: String,
)
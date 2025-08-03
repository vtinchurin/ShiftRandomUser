package ru.vtinchurin.shiftrandomuser.core.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import ru.vtinchurin.shiftrandomuser.detail.domain.model.UserDetail
import ru.vtinchurin.shiftrandomuser.list.domain.model.User

data class UserAndImage(
    @Embedded val user: UserCache,
    @Relation(
        parentColumn = "image_id",
        entityColumn = "id",
    )
    val image: ImageCache
)

fun UserAndImage.toUserDetail() = UserDetail(
    id = user.id,
    name = user.fullName,
    imageUrl = image.large,
    email = user.email,
    phone = user.phone,
    city = user.city,
    longitude = user.longitude,
    latitude = user.latitude,
)

fun UserAndImage.toUser() = User(
    id = user.id,
    name = user.fullName,
    imageUrl = image.small,
    email = user.email,
    city = user.city,
    phone = user.phone,
)
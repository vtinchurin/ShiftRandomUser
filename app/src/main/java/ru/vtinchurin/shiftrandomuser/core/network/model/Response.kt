package ru.vtinchurin.shiftrandomuser.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonPrimitive
import ru.vtinchurin.shiftrandomuser.core.local.entity.ImageCache
import ru.vtinchurin.shiftrandomuser.core.local.entity.UserCache

@Serializable
data class Response(
    @SerialName("results")
    val results: ArrayList<UserInfo>,
    @SerialName("info")
    val info: Info,
)

@Serializable
data class UserInfo(
    @SerialName("gender")
    val gender: String,
    @SerialName("name")
    val name: Name,
    @SerialName("location")
    val location: Location,
    @SerialName("email")
    val email: String,
    @SerialName("login")
    val login: Login,
    @SerialName("dob")
    val dob: Dob,
    @SerialName("registered")
    val registered: Registered,
    @SerialName("phone")
    val phone: String,
    @SerialName("cell")
    val cell: String,
    @SerialName("id")
    val id: Id,
    @SerialName("picture")
    val picture: Picture,
    @SerialName("nat")
    val nat: String,
)

fun UserInfo.toUserCache(index: Long) = UserCache(
    id = index,
    fullName = name.first + " " + name.last,
    email = email,
    phone = phone,
    country = location.country,
    city = location.city,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
)

fun UserInfo.toImageCache(index: Long) = ImageCache(
    id = index,
    small = picture.medium,
    large = picture.large,
)

@Serializable
data class Name(
    @SerialName("title")
    val title: String,
    @SerialName("first")
    val first: String,
    @SerialName("last")
    val last: String,
)

@Serializable
data class Street(
    @SerialName("number")
    val number: Int,
    @SerialName("name")
    val name: String,
)

@Serializable
data class Coordinates(
    @SerialName("latitude")
    val latitude: String,
    @SerialName("longitude")
    val longitude: String,
)

@Serializable
data class Timezone(
    @SerialName("offset")
    val offset: String,
    @SerialName("description")
    val description: String,
)

@Serializable
data class Location(
    @SerialName("street")
    val street: Street,
    @SerialName("city")
    val city: String,
    @SerialName("state")
    val state: String,
    @SerialName("country")
    val country: String,
    @SerialName("postcode")
    @Serializable(with = PostcodeSerializer::class)
    val postcode: String,
    @SerialName("coordinates")
    val coordinates: Coordinates,
    @SerialName("timezone")
    val timezone: Timezone,
) {
    object PostcodeSerializer : JsonTransformingSerializer<String>(String.serializer()) {
        override fun transformDeserialize(element: JsonElement): JsonElement {
            return if (element.jsonPrimitive.isString) {
                element
            } else JsonPrimitive("")
        }
    }
}


@Serializable
data class Login(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("salt")
    val salt: String,
    @SerialName("md5")
    val md5: String,
    @SerialName("sha1")
    val sha1: String,
    @SerialName("sha256")
    val sha256: String,
)

@Serializable
data class Dob(
    @SerialName("date")
    val date: String,
    @SerialName("age")
    val age: Int,
)

@Serializable
data class Registered(
    @SerialName("date")
    val date: String,
    @SerialName("age")
    val age: Int,
)

@Serializable
data class Id(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    @Serializable(with = ValueSerializer::class)
    val value: String,
) {

    object ValueSerializer : JsonTransformingSerializer<String>(String.serializer()) {
        override fun transformDeserialize(element: JsonElement): JsonElement {
            return when (element) {
                is JsonNull -> JsonPrimitive("null")
                else -> element
            }
        }
    }
}

@Serializable
data class Picture(
    @SerialName("large")
    val large: String,
    @SerialName("medium")
    val medium: String,
    @SerialName("thumbnail")
    val thumbnail: String,
)

@Serializable
data class Info(
    @SerialName("seed")
    val seed: String,
    @SerialName("results")
    val results: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("version")
    val version: String,
)
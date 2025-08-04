package ru.vtinchurin.shiftrandomuser.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.vtinchurin.shiftrandomuser.core.local.entity.ImageCache
import ru.vtinchurin.shiftrandomuser.core.local.entity.UserAndImage
import ru.vtinchurin.shiftrandomuser.core.local.entity.UserCache

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: UserCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(vararg images: ImageCache): List<Long>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun loadUserDetail(id: Long): UserAndImage

    @Transaction
    @Query("SELECT * FROM users")
    fun loadUsers(): Flow<List<UserAndImage>>

    @Query("SELECT EXISTS(SELECT 1 FROM users) AS has_rows")
    suspend fun hasUsers(): Boolean

    @Transaction
    suspend fun insertUsersWithImages(users: List<UserCache>, images: List<ImageCache>) {
        val imageIds = insertImages(*images.toTypedArray())

        val usersWithCorrectImageIds = users.mapIndexed { index, user ->
            user.copy(imageId = imageIds[index])
        }
        insertUsers(*usersWithCorrectImageIds.toTypedArray())
    }
}
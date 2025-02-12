package com.github.list.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.list.data.local.entity.LocalGithubUser

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<LocalGithubUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<LocalGithubUser>)

}

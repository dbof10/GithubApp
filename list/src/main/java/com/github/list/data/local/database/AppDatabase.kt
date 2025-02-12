package com.github.list.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.list.data.local.dao.UsersDao
import com.github.list.data.local.entity.LocalGithubUser

@Database(entities = [LocalGithubUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}

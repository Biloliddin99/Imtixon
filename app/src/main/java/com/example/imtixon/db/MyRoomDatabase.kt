package com.example.imtixon.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imtixon.models.Users

@Database(entities = [Users::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {
        fun newInstance(context: Context): MyRoomDatabase {
            return Room.databaseBuilder(context,MyRoomDatabase::class.java,"Users")
                .allowMainThreadQueries()
                .build()
        }
    }
}
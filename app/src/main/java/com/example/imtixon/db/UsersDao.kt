package com.example.imtixon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.imtixon.models.Users

@Dao
interface UsersDao {
    @Insert
    fun addUsers(users: ArrayList<Users>)

    @Query("select *from users")
    fun getAllUsers():List<Users>

    @Query("select *from users where title like '%' || :searchQuery || '%' or id like '%' ||:searchQuery ")
    fun searchQuery(searchQuery: String):List<Users>


}
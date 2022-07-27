package com.example.wheretogo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wheretogo.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUserList(): List<User>

    @Insert
    fun insert(user:User)


    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String) : User?



}
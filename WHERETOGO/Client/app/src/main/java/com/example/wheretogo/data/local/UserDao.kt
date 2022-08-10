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

    //존재할 경우만 DB에 넣어주도록
    @Query("SELECT EXISTS (SELECT * FROM User WHERE idx = :userIdx)")
    fun isUserExist(userIdx:Int) : Boolean

    @Query("SELECT email FROM USER WHERE idx= :userIdx")
    fun getEmail(userIdx:Int) : String

    @Query("SELECT nickName FROM USER WHERE idx= :userIdx")
    fun getNickname(userIdx:Int) : String
//
//    @Query("UPDATE USER SET nickName=:nickName WHERE idx=:userIdx")
//    fun setNickName(userIdx:Int, nickName: String )
//
//    @Query("UPDATE USER SET email=:email WHERE idx=:userIdx")
//    fun setEmail(userIdx:Int, email: String )
//
//    @Query("UPDATE USER SET imgUri=:imgUri WHERE idx=:userIdx")
//    fun setImgUrl(userIdx:Int, imgUri: Int )

    @Query("DELETE FROM User WHERE idx = :userIdx")
    fun deleteUser(userIdx:Int)


}
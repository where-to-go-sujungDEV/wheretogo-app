package com.example.wheretogo.data.local

import androidx.room.*
import com.example.wheretogo.data.entities.Event
import com.example.wheretogo.data.entities.User
import com.example.wheretogo.data.entities.userSavedEvent

@Dao
interface UserSavedEventDao {
    @Query("SELECT * FROM userSavedEvent")
    fun getAll(): List<userSavedEvent>

    @Query("SELECT * FROM userSavedEvent WHERE title= :title")
    fun getUserSavedEvent(title:String) : userSavedEvent

    @Query("SELECT isLike FROM userSavedEvent WHERE title= :title")
    fun getIsLike(title:String) : Boolean

    @Query("SELECT isVisited FROM userSavedEvent WHERE title= :title")
    fun getIsVisited(title:String) : Boolean

    @Query("SELECT * FROM userSavedEvent WHERE isLike = :isLike")
    fun getLikeEvents(isLike:Boolean) : List<userSavedEvent>

    @Query("SELECT * FROM userSavedEvent WHERE isvisited = :isVisited")
    fun getVisitedEvents(isVisited:Boolean) : List<userSavedEvent>


    @Query("DELETE FROM userSavedEvent WHERE title = :title")
    fun deleteUserSavedEvent(title:String) : Int

    @Query("UPDATE userSavedEvent SET isLike= :isLike")
    fun updateIsLike(isLike:Boolean) : Void

    @Query("UPDATE userSavedEvent SET isVisited= :isVisited")
    fun updateIsVisited(isVisited:Boolean) : Void


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userSavedEvent: userSavedEvent)

    @Delete
    fun deleteAll(vararg userSavedEvent: userSavedEvent)



}
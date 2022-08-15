package com.example.wheretogo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.wheretogo.data.entities.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

//    @Query("SELECT * FROM event WHERE name = :name")
//    fun getEvent(name:String) : Event

    @Insert()
    fun insert(event : Event)

    @Delete
    fun deleteEvent(events: Event)

}
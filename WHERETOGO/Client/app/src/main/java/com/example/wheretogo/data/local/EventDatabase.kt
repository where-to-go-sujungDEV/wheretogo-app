package com.example.wheretogo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wheretogo.data.entities.Event

@Database(entities= [Event::class], version=2)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object{
        private var INSTANCE: EventDatabase? =null

        fun getInstance(context: Context):EventDatabase?{
            if (INSTANCE == null){
                synchronized(EventDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    EventDatabase::class.java, "event.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}
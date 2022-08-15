package com.example.wheretogo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wheretogo.data.entities.userSavedEvent


@Database(entities= [userSavedEvent::class], version=2)
abstract class UserSavedEventDatabase: RoomDatabase() {
    abstract fun userSavedEventDao(): UserSavedEventDao

    companion object{
        private var INSTANCE: UserSavedEventDatabase? =null

        fun getInstance(context: Context):UserSavedEventDatabase?{
            if (INSTANCE == null){
                synchronized(UserSavedEventDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserSavedEventDatabase::class.java, "userSavedEvent.db")
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
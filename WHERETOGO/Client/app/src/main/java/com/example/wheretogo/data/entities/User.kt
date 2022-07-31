package com.example.wheretogo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var email: String,
    var password: String,
    var name: String
//    var sex: String,
//    var age: Int
)    {@PrimaryKey(autoGenerate = true) var idx: Int = 0}
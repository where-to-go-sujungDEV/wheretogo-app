package com.example.wheretogo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var email: String,
    var password: String,
    var nickname: String,
    var sex: String,
    var age: Int
)
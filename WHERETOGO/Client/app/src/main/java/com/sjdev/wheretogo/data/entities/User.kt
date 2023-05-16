package com.sjdev.wheretogo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var idx: Int = 0,
    var nickname: String,
    var email: String,
    var password: String,
    var sex: String,
    var age: Int
)
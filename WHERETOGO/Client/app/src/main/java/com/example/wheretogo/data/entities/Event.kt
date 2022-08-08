package com.example.wheretogo.data.entities

import androidx.room.Entity
import java.sql.Date
import java.util.*

@Entity
data class Event(
    var name: String,
    var startDate : String,
    var endDate : String,
    var hashtag1 :String,
    var hashtag2 :String,
    var hashtag3 :String
){}

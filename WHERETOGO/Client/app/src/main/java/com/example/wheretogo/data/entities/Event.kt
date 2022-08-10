package com.example.wheretogo.data.entities

import androidx.room.Entity
import java.sql.Date
import java.util.*

@Entity
data class Event(
    var eventId: Int,
    var name: String,

    var genre: String,
    var theme : String,

    var hashtag1:String,
    var hashtag2:String,
    var hashtag3:String,

    var startDate: String,
    var endDate: String,
    var savedNum: Int,

    var pic: String,

    var isvisited : Boolean,
    var isLiked : Boolean
){}

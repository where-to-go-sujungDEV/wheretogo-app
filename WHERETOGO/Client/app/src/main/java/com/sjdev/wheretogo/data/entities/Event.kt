package com.sjdev.wheretogo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey var eventID: Int,
    var eventName: String,
    var dou: String,
    var si: String,

    var genre: String,
    var theme : String,
    var kind : String,

    var startDate: String,
    var endDate: String,

    var pic: String,
    var time: String,
    var place: String,
    var link: String,
    var cost: String,
    var content: String,

    var w1 : Int,
    var m1 : Int,
    var w2 : Int,
    var m2 : Int,
    var w3 : Int,
    var m3 : Int,
    var w4 : Int,
    var m4 : Int,
    var w6 : Int,
    var m6 : Int

)

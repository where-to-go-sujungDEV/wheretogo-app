package com.example.wheretogo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserSavedEvent(
    @PrimaryKey var coverImg: Int? = null,
    val tag : String = "",
    val title : String = "",
    var date: String = "",
    var isLike: Boolean? = false,
    var isVisited: Boolean? = false
)
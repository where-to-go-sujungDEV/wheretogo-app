package com.example.wheretogo.data


data class userSavedEvent(
    var coverImg: Int? = null,
    val tag : String = "",
    val title : String = "",
    var date: String = "",
    var isLike: Boolean? = false,
    var isVisited: Boolean? = false
)
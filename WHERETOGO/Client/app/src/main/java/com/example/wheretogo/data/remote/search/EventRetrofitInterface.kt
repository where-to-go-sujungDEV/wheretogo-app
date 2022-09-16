package com.example.wheretogo.data.remote.search

import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface EventRetrofitInterface {
   // @GET("/search/{search}/{aCode}/{aDCode}/{fromD}/{toD}/{k1}/{k2}/{k3}/{k4}/{k5}/{k6}/{k7}/{k8}/{k9}/{k10}/{k11}/{k12}/{k13}/{k14}/{k15}/{free}/{align}")
   @GET("/search")
    fun getEvents(
       @Query("search") search:String?,
       @Query("aCode") aCode: Int?,
       @Query("aDCode") aDCode: Int?,
       @Query("fromD") fromD: String?,
       @Query("toD") toD: String?,
       @Query("k1") k1:Int?,
       @Query("k2") k2:Int?,
       @Query("k3") k3:Int?,
       @Query("k4") k4:Int?,
       @Query("k5") k5:Int?,
       @Query("k6") k6:Int?,
       @Query("k7") k7:Int?,
       @Query("k8") k8:Int?,
       @Query("k9") k9:Int?,
       @Query("k10") k10:Int?,
       @Query("k11") k11:Int?,
       @Query("k12") k12:Int?,
       @Query("k13") k13:Int?,
       @Query("k14") k14:Int?,
       @Query("k15") k15:Int?,
       @Query("free") free: Int?,
       @Query("align") align: String?
    ): Call<EventResponse>

}
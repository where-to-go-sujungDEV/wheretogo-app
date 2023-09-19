package com.sjdev.wheretogo.data.remote.review

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ReviewInterface {
//    @Multipart
//    @POST("visited/review/sv/{eventID}")
//    fun sendReview(
//        @Path("eventID") eventID: Part,
//        @Part star : Part,
//        @Part companionId : Part,
//        @Part review : Part,
//        @Part isPrivate : Part,
//        @Part pic : MultipartBody.Part
//        ) : Call<PostReviewResponse>

//    @Multipart
//    @POST("visited/review/sv/{eventID}")
//    fun sendReview(
//        @Path("eventID") eventID: Int,
//        @Part star : RequestBody,
//        @Part companionId : RequestBody,
//        @Part review : RequestBody,
//        @Part isPrivate : RequestBody,
//        @Part pic : MultipartBody.Part?
//    ) : Call<PostReviewResponse>

    @Multipart
    @PATCH("visited/review/sv/{eventID}")
    fun sendReview(
        @Path("eventID") eventID: Int,
//        @Part pic : MultipartBody.Part?,
        @PartMap data : HashMap<String, RequestBody>
    ) : Call<PostReviewResponse>
}
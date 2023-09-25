package com.sjdev.wheretogo.data.remote.review

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ReviewInterface {
    @Multipart
    @PATCH("visited/review/sv/{eventID}")
    fun sendReview(
        @Path("eventID") eventID: Int,
        @Part pic : MultipartBody.Part?,
        @PartMap data : HashMap<String, RequestBody>
    ) : Call<PostReviewResponse>
}
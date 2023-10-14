package com.sjdev.wheretogo.data.remote.review

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ReviewInterface {
    /**
     * 리뷰 작성
     */
    @Multipart
    @PATCH("visited/review/sv/{eventID}")
    fun sendReview(
        @Path("eventID") eventID: Int,
        @Part pic : MultipartBody.Part?,
        @PartMap data : HashMap<String, RequestBody>
    ) : Call<PostReviewResponse>

    /**
     * 모든 리뷰 조회
     */
    @GET("visited/review/list/{eventID}/0")
    fun showAllReviews(@Path("eventID") eventID: Int): Call<EventReviewResponse>
}
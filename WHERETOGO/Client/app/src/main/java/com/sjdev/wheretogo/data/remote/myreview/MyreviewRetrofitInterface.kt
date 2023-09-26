package com.sjdev.wheretogo.data.remote.myreview


import retrofit2.Call
import retrofit2.http.*

interface MyreviewRetrofitInterface {

    /** 리뷰 상세 가져오기 (회원용) **/
    @GET("/visited/review/m/{reviewID}")
    fun getReviewDetailForMembers(@Path("reviewID") reviewID: Int): Call<ReviewDetailResponse>


    /** 리뷰 리스트 가져오기 (회원용) **/
    @GET("/visited/review/list/{eventID}/{align}")
    fun getReviewListForMembers(@Path("eventID") eventID: Int): Call<ReviewDetailResponse>

    /** eventID로 이벤트 정보 가져오기 **/
    @GET("/event/{eventID}")
    fun getEventName(@Path("eventID")eventID: Int):Call<AboutEventResponse>

    /** 리뷰 삭제하기 **/
    @PATCH("/visited/review/rm/{eventID}")
    fun deleteReview(@Path("eventID")eventID: Int): Call<DeleteReviewResponse>

}
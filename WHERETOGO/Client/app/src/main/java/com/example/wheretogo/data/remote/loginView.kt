package com.example.wheretogo.data.remote

interface LoginView {
    fun onLoginSuccess(result: UserResult)
    fun onLoginFailure(message: String)

}
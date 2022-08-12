package com.example.wheretogo.data.remote.Auth

interface LoginView {
    fun onLoginSuccess(result: UserResult)
    fun onLoginFailure(message: String)

}
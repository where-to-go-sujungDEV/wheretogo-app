package com.example.wheretogo.data.remote.auth

interface LoginView {
    fun onLoginSuccess(result: UserResult)
    fun onLoginFailure(message: String)

}
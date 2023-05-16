package com.sjdev.wheretogo.data.remote.auth

interface LoginView {
    fun onLoginSuccess(result: UserResult)
    fun onLoginFailure(message: String)

}
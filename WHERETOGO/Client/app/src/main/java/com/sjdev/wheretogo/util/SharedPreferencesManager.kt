package com.sjdev.wheretogo.util

import com.sjdev.wheretogo.util.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.sjdev.wheretogo.util.ApplicationClass.Companion.mSharedPreferences

//JWT
fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun removeJwt(){
    val editor = mSharedPreferences.edit()
    editor.remove(X_ACCESS_TOKEN)
    editor.apply()
}

//유저 닉네임
fun saveNickname(name: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("nickname", name)
    editor.apply()
}

fun getNickname(): String? = mSharedPreferences.getString("nickname", "USER")

//유저 이메일
fun saveEmail(email : String){
    val editor = mSharedPreferences.edit()
    editor.putString("email", email)

    editor.apply()
}
fun getEmail() : String? = mSharedPreferences.getString("email",null)

fun removeEmail(){
    val editor = mSharedPreferences.edit()
    editor.remove("email")
    editor.apply()
}

fun saveAge(age:Int){
    val editor = mSharedPreferences.edit()
    editor.putInt("age", age)
    editor.apply()
}

fun saveSex(sex:String){
    val editor = mSharedPreferences.edit()
    editor.putString("sex", sex)
    editor.apply()
}

fun getAge():Int?=mSharedPreferences.getInt("age",1)

fun getSex() : String? = mSharedPreferences.getString("sex",null)


package com.sjdev.wheretogo.util

import android.app.AlertDialog
import android.content.Context

fun showDialog(context: Context,msg: Int){
    AlertDialog.Builder(context)
        .setMessage(msg)
        .setPositiveButton("확인") { _, _ ->
        }
        .show()
}
fun showStringDialog(context: Context,msg: String){
    AlertDialog.Builder(context)
        .setMessage(msg)
        .setPositiveButton("확인") { _, _ ->
        }
        .show()
}


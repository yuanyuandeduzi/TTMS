package com.example.ttms.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope

object ToastUtils {
    fun toast(context: Context, content: String) {
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
    }
}
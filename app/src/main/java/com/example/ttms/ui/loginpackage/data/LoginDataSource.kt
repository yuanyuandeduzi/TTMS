package com.example.ttms.ui.loginpackage.data

import android.util.Log
import com.example.logintest.network.UploadUtil
import com.example.ttms.ui.loginpackage.data.model.LoggedInUser
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val map = mapOf("username" to username, "password" to password)

            val loginPostCall = UploadUtil.loginService
                .loginPostCall(
                    Gson().toJson(map).toRequestBody("application/json".toMediaTypeOrNull())
                )
            val baseResponse = UploadUtil.postService.getTheatreMessageGetCall()
            Log.d("TAG", "login: ${baseResponse.data}")
            val fakeUser = LoggedInUser
            fakeUser.apply {
                data = loginPostCall.data
                msg = loginPostCall.msg
                code = loginPostCall.code
            }
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
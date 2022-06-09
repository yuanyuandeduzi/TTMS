package com.example.ttms.ui.addplan

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.network.UploadUtil
import com.example.ttms.util.TimeUtils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AddPlanMessageAcVm : ViewModel() {

    data class Data(
        var movieId: String? = "",
        var studioId: String? = "",
        var startTime: String = "",
        var endTime: String = "",
        var ticketPrice: String = "",
        var status: String = ""
    )


    private val _data by lazy {
        MutableLiveData<Data>()
    }
    val data : LiveData<Data>
        get() {
            return _data
        }

    fun alterData(startTime: String,endTime: String,ticketPrice: String) {
        _data.value = _data.value?.also {
            it.startTime = startTime
            it.endTime = endTime
            it.ticketPrice = ticketPrice.toDoubleOrNull().toString()
        }
    }

    fun checkedBt() : Boolean {
        _data.value?.let {
            if(it.endTime == "" || it.startTime == "" || it.ticketPrice == "") {
                return false
            }
        }
        return true
    }

    fun setData(intent : Intent) {
        intent.also {
            val movieId = it.getStringExtra("movieId")
            val theatreId = it.getStringExtra("theatreId")
            _data.value = Data(movieId,theatreId,"","","","1")
        }
    }

    fun upload() {
        viewModelScope.launch {
            _data.value?.let {
                it.startTime = TimeUtils.timeToMs(it.startTime)
                it.endTime = TimeUtils.msAddMin(it.startTime,it.endTime)
            }
            Log.d("TAG", "upload: ${_data.value}")

            val bool = UploadUtil.postService.uploadPlanPostCall(
                Gson().toJson(_data.value).toRequestBody("application/json".toMediaTypeOrNull())
            )
            Log.d("TAG", "upload: $bool")
        }
    }
}
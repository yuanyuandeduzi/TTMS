package com.example.ttms.ui.Theatrepackage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.network.UploadUtil
import com.example.ttms.bean.TheatreData
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AddTheatreAcVm : ViewModel() {

    private val _message: MutableLiveData<TheatreData> by lazy {
        MutableLiveData<TheatreData>().also {
            it.value = TheatreData()
            it.value?.status = 1
        }
    }
    val message: LiveData<TheatreData>
        get() {
            return _message
        }

    //上传结果
    private val _result: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val result: LiveData<Boolean>
        get() {
            return _result
        }


    fun theatreDataChanged(row: String, column: String, name: String) {
        _message.value = _message.value.also {
                if (column != "") {
                    it?.col = column.toInt()
                } else {
                    it?.col = 0
                }
                if (row != "") {
                    it?.row = row.toInt()
                } else {
                    it?.row = 0
                }
                it?.name = name
            }
    }

    //0长宽不合法，1名称不合法，2成功
    fun isFinish(): Int {
        _message.value?.let {
            if (it.col <= 0 || it.row <= 0) {
                return 0
            }
            if (it.name == "") {
                return 1
            }
            return 2
        }
        return 0
    }

    fun uploadTheatre() {
        viewModelScope.launch {
            val mag = _message.value
            val map = mapOf(
                "name" to mag?.name,
                "row" to mag?.row.toString(),
                "col" to mag?.col.toString(),
                "status" to mag?.status.toString()
            )
            Log.d("TAG", "uploadTheatre: $map")
            val bool = UploadUtil.postService.insertTheatrePostCall(
                Gson().toJson(map).toRequestBody("application/json".toMediaTypeOrNull())
            ).data
            _result.postValue(bool)
        }
    }
}
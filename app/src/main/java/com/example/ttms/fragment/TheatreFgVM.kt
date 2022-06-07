package com.example.ttms.fragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.network.UploadUtil
import com.example.ttms.bean.TheatreData
import com.example.ttms.util.ToastUtils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class TheatreFgVM : ViewModel() {

    private val _message: MutableLiveData<List<TheatreData>> by lazy {
        MutableLiveData<List<TheatreData>>().also {
            viewModelScope.launch {
                val baseResponse = UploadUtil.postService.getTheatreMessageGetCall()
                it.postValue(baseResponse.data)
            }
        }
    }
    val message: LiveData<List<TheatreData>>
        get() = _message

    //删除演出厅
    fun removeByIndex(context : Context, one : TheatreData) {
        val list = _message.value as ArrayList<TheatreData>
        list.remove(one)
        viewModelScope.launch {
            val map = mapOf("id" to one.id.toString())
            val result = UploadUtil.postService.deleteTheatreByIdPostCall(
                Gson().toJson(map).toRequestBody("application/json".toMediaTypeOrNull())
            )
            if(result.data == true) {
                ToastUtils.toast(context, "删除成功")
            }else {
                ToastUtils.toast(context, "删除失败")
            }
        }
    }

    //重新刷新数据
    fun refresh() {
        viewModelScope.launch {
            val baseResponse = UploadUtil.postService.getTheatreMessageGetCall()
            _message.postValue(baseResponse.data)
        }
    }
}
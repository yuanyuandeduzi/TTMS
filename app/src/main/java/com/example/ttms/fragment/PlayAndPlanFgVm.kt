package com.example.ttms.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.network.UploadUtil
import com.example.ttms.bean.DataSource
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody

class PlayAndPlanFgVm : ViewModel() {
    private var page = 1

    private val _dataSource by lazy {
        MutableLiveData<MutableList<DataSource>>().also {
            it.value = mutableListOf()
        }
    }
    val dataSource: LiveData<MutableList<DataSource>>
        get() {
            return _dataSource
        }

    fun addNextPage() {
        viewModelScope.launch {
            val map = mapOf(
                "sortType" to "rate",
                "sortRule" to "down",
                "page" to page.toString(),
                "pageLimit" to "10"
            )
            _dataSource.value = _dataSource.value.also {
                UploadUtil.postService.getPlayMessageGetCall(map).data?.dataSource?.let { it1 ->
                    it?.addAll(
                        it1
                    )
                }
            }
            page++
        }
    }

}
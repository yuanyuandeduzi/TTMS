package com.example.ttms.ui.addplan

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.network.UploadUtil
import com.example.ttms.bean.PlanData
import com.example.ttms.bean.PlanMagData
import kotlinx.coroutines.launch
import java.lang.Exception

class PlanMessageAcVm : ViewModel() {

    private val _movieId by lazy {
        MutableLiveData<String>()
    }
    val movieId: LiveData<String>
        get() {
            return _movieId
        }

    fun setMovieId(intent: Intent) {
        intent.let {
            Log.d("TAG", "setMovieId: ${it.getStringExtra("movieId")}")
            _movieId.value = it.getStringExtra("movieId")
        }
    }

    private val _planMessage by lazy {
        MutableLiveData<List<PlanData>>().also {
           try{
               viewModelScope.launch {
                   val map = mapOf(
                       "movieId" to _movieId.value.toString(),
                       "sortName" to "scheduleId",
                       "sortRule" to "down",
                       "page" to "1",
                       "pageLimit" to "20"
                   )
                   val planData = UploadUtil.postService.getPlanMessageGetCall(map).data?.planData
                   Log.d("TAG", ":11 $planData")
                   it.postValue(planData)
               }
           }catch (e : Exception) {
               it.value = listOf()
           }
        }
    }
    val planMessage : LiveData<List<PlanData>>
        get() {
            return _planMessage
        }

    fun refresh() {
        viewModelScope.launch {
            val map = mapOf(
                "movieId" to _movieId.value.toString(),
                "sortName" to "scheduleId",
                "sortRule" to "down",
                "page" to "1",
                "pageLimit" to "20"
            )
            val planData = UploadUtil.postService.getPlanMessageGetCall(map).data?.planData
            Log.d("TAG", ":11 $planData")
            _planMessage.postValue(planData)
        }
    }
}
package com.example.ttms.ui.addplan

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.network.UploadUtil
import com.example.ttms.bean.TheatreData
import kotlinx.coroutines.launch

class AddPlanSelectTheatreAcVm : ViewModel(){

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

    var movieId : String? = null

    fun setMovieId(intent : Intent) {
        movieId = intent.getStringExtra("movieId")
    }
}
package com.example.ttms.ui.playpackage

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttms.bean.DataSource

class PlayAcVm :ViewModel() {

    private val _dataSource by lazy {
        MutableLiveData<DataSource>()
    }
    val dataSource : LiveData<DataSource>
        get() {
            return _dataSource
        }

    fun setDataSource(intent : Intent) {
        intent.let{
            _dataSource.value = it.getSerializableExtra("data") as DataSource?
        }
    }
}
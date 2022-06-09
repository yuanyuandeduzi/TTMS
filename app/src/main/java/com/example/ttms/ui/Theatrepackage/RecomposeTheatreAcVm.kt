package com.example.ttms.ui.Theatrepackage

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.network.UploadUtil
import com.example.ttms.bean.SeatData
import com.example.ttms.bean.TheatreData
import com.example.ttms.util.SeatTheatreUtils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class RecomposeTheatreAcVm : ViewModel() {

    //剧院对象
    private val _theatre by lazy {
        MutableLiveData<TheatreData>()
    }
    val theatre: LiveData<TheatreData>
        get() {
            return _theatre
        }

    //实例化剧院对象
    fun setTheatre(intent: Intent) {
        intent.let {
            val theatreData = TheatreData(
                it.getIntExtra("col", 10),
                it.getIntExtra("id", 100),
                it.getStringExtra("name") ?: "",
                it.getIntExtra("row", 10),
                it.getIntExtra("status", 0)
            )
            _theatre.value = theatreData
        }
    }

    //剧院名称相关
    private val _name by lazy {
        MutableLiveData<String>()
    }
    val name: LiveData<String>
        get() {
            return _name
        }

    //修改剧院名
    fun setName(name: String) {
        _name.value = name
    }

    //名称置null
    fun setNameNull() {
        _theatre.value = _theatre.value?.also {
            it.name = _name.value ?: ""
        }
        uploadTheatre()
        _name.value = ""
    }

    //检查输入框
    fun checkName(): Boolean {
        return (_name.value != "")
    }

    //剧院addRow相关
    private val _addRow by lazy {
        MutableLiveData<String>()
    }
    val addRow: LiveData<String>
        get() {
            return _addRow
        }

    fun setAddRow(row: String) {
        _addRow.value = row
    }

    fun setAddRowNull() {
        _theatre.value = _theatre.value?.also {
            it.row = it.row + _addRow.value.toString().toInt()
        }
        uploadTheatre()
        _addRow.value = ""
    }

    fun checkAddRow(): Boolean {
        return (_addRow.value != "")
    }

    //剧院delRow相关
    private val _delRow by lazy {
        MutableLiveData<String>()
    }
    val delRow: LiveData<String>
        get() {
            return _delRow
        }

    fun setDelRow(row: String) {
        _delRow.value = row
    }

    fun setDelRowNull() {

        _theatre.value = _theatre.value?.also {
            it.row = it.row - _delRow.value.toString().toInt()
        }

        uploadTheatre()
        _delRow.value = ""
    }

    fun checkDelRow(): Boolean {
        return (_delRow.value != "")
    }

    //剧院addCol相关
    private val _addCol by lazy {
        MutableLiveData<String>()
    }
    val addCol: LiveData<String>
        get() {
            return _addCol
        }

    fun setAddCol(col: String) {
        _addCol.value = col
    }

    fun setAddColNull() {
        _theatre.value = _theatre.value?.also {
            it.col = it.col + _addCol.value.toString().toInt()
        }
        uploadTheatre()
        _addCol.value = ""
    }

    fun checkAddCol(): Boolean {
        return (_addCol.value != "")
    }

    //剧院delCol相关
    private val _delCol by lazy {
        MutableLiveData<String>()
    }
    val delCol: LiveData<String>
        get() {
            return _delCol
        }

    fun setDelCol(col: String) {
        _delCol.value = col
    }

    fun setDelColNull() {
        _theatre.value = _theatre.value?.also {
            it.col = it.col - _delCol.value.toString().toInt()
        }
        uploadTheatre()
        _addCol.value = ""
    }

    fun checkDelCol(): Boolean {
        return (_delCol.value != "")
    }

    //座位相关
    private val _seatMessage by lazy {
        MutableLiveData<Map<Int, SeatData>>().also {
            viewModelScope.launch {
                val list =
                    UploadUtil.postService.getSeatsGetCall(_theatre.value?.id.toString()).data
                list?.let {
                    SeatTheatreUtils.setFirstId(it[0].seatId)
                }
                val map = list?.let {
                    SeatTheatreUtils.listToMap(it)
                }
                it.value = map
            }
        }
    }
    val seatMessage: LiveData<Map<Int, SeatData>>
        get() {
            return _seatMessage
        }

    private val _seatList by lazy {
        MutableLiveData<MutableList<SeatData>>().also {
            it.value = mutableListOf<SeatData>()
        }
    }
    val seatData: LiveData<MutableList<SeatData>>
        get() {
            return _seatList
        }

    fun addSeat(seat: SeatData) {
        _seatList.value = _seatList.value?.also {
            it.add(seat)
        }
    }

    fun delSeat(seat: SeatData) {
        _seatList.value = _seatList.value?.also {
            it.remove(seat)
        }
    }

    fun checkList(): Boolean {
        return _seatList.value?.size != 0
    }

    //添加座位相关
    class RowAndCol(var addRow: Int, var addCol: Int)

    private val _addSeat by lazy {
        MutableLiveData<RowAndCol>().also {
            it.value = RowAndCol(0, 0)
        }
    }
    val addSeat: LiveData<RowAndCol>
        get() {
            return _addSeat
        }

    fun setRowAndCol(row: String, col: String) {
        if (row != "" && col != "") {
            _addSeat.value = _addSeat.value?.also {
                it.addCol = col.toInt()
                it.addRow = row.toInt()
            }
        } else {
            _addSeat.value = _addSeat.value?.also {
                it.addCol = -1
                it.addRow = -1
            }
        }
    }

    fun checkAddSeat(): Boolean {
        _addSeat.value?.let {
            if (it.addCol > 0 && it.addRow > 0) {
                return true
            }
        }
        return false
    }

    fun setAddSeatNull() {
        _addSeat.value = _addSeat.value?.also {
            val theatre = _theatre.value!!
            if (it.addRow > theatre.row || it.addCol > theatre.col) {
                return
            }
            val seatData = SeatTheatreUtils.getSeatByRC(
                _seatMessage.value!!,
                it.addRow,
                it.addCol,
                _theatre.value!!
            )
            seatData.status = 1
            viewModelScope.launch {
                UploadUtil.postService.alterSeatBySeatPostCall(
                    Gson().toJson(seatData).toRequestBody("application/json".toMediaTypeOrNull())
                )
            }
            it.addRow = -1
            it.addCol = -1
        }
        _seatMessage.value = _seatMessage.value
    }

    //共用函数相关

    fun uploadSeatMessage() {
        viewModelScope.launch {
            val list =
                UploadUtil.postService.getSeatsGetCall(_theatre.value?.id.toString()).data
            list?.let {
                SeatTheatreUtils.setFirstId(it[0].seatId)
            }
            val map = list?.let {
                SeatTheatreUtils.listToMap(it)
            }
            Log.d("TAG", "uploadSeatMessage: ${map?.size}")
            _seatMessage.value = map
        }
    }

    private val _uploadIsFinish: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also {
            it.value = false
        }
    }
    val uploadIsFinish: LiveData<Boolean>
        get() {
            return _uploadIsFinish
        }


    //上传演出厅信息
    private fun uploadTheatre() {
        viewModelScope.launch {
            val bool = UploadUtil.postService.alterTheatrePostCall(
                Gson().toJson(_theatre.value).toRequestBody("application/json".toMediaTypeOrNull())
            ).data
            _uploadIsFinish.postValue(bool)
        }
    }

    //修改座位状态
    fun uploadSeat(list: List<SeatData>, status: Int) {
        for (seatData in list) {
            seatData.status = status
            viewModelScope.launch {
                UploadUtil.postService.alterSeatBySeatPostCall(
                    Gson().toJson(seatData).toRequestBody("application/json".toMediaTypeOrNull())
                )
            }
            _seatMessage.value = _seatMessage.value?.also {
                val id = SeatTheatreUtils.getSeatByRC(
                    it,
                    seatData.row,
                    seatData.col,
                    _theatre.value!!
                ).seatId
                it[id]?.status = status
            }
        }

        _seatList.value?.clear()
    }

}
package com.example.ttms.ui.Theatrepackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.ttms.bean.SeatData
import com.example.ttms.bean.TheatreData
import com.example.ttms.customizedView.SeatTable
import com.example.ttms.databinding.ActivityRecomposetheatreBinding
import com.example.ttms.ui.loginpackage.ui.login.afterTextChanged
import com.example.ttms.util.SeatTheatreUtils

class RecomposeTheatreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecomposetheatreBinding
    private val viewModel by lazy { ViewModelProvider(this)[RecomposeTheatreAcVm::class.java] }
    private val seatTable by lazy { binding.seatTable }
    private val seatMap by lazy {
        viewModel.seatMessage.value ?: mapOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomposetheatreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //获取上一活动传的数据
        intent?.let {
            viewModel.setTheatre(intent)
        }
        //当剧院信息变化刷新界面
        viewModel.theatre.observe(this) {
            initSeatTable(seatTable, it, seatMap)
            //在这重新给map值
        }

        viewModel.uploadIsFinish.observe(this) {
            if (it) {
                viewModel.uploadSeatMessage()
            }
        }

        //修改名称相关
        val btName = binding.setTheatreBtName
        binding.setTheatreEdtInputName.afterTextChanged {
            viewModel.setName(it)
        }
        viewModel.name.observe(this) {
            btName.isEnabled = false
            if (viewModel.checkName()) {
                btName.isEnabled = true
            }
        }
        btName.setOnClickListener {
            btName.isEnabled = false
            //SeatTheatreUtils.alterName(seatTable, viewModel.name.value ?: "")
            viewModel.setNameNull()
            binding.setTheatreEdtInputName.text = null
        }

        //添加row相关
        val btAddRow = binding.setTheatreBtAddRow
        binding.setTheatreEdtAddRow.afterTextChanged {
            viewModel.setAddRow(it)
        }
        viewModel.addRow.observe(this) {
            btAddRow.isEnabled = false
            if (viewModel.checkAddRow()) {
                btAddRow.isEnabled = true
            }
        }
        btAddRow.setOnClickListener {
            btAddRow.isEnabled = false
            viewModel.setAddRowNull()
            binding.setTheatreEdtAddRow.text = null
        }
        //删除row相关
        val btDelRow = binding.setTheatreBtDelRow
        binding.setTheatreEdtDelRow.afterTextChanged {
            viewModel.setDelRow(it)
        }
        viewModel.delRow.observe(this) {
            btDelRow.isEnabled = false
            if (viewModel.checkDelRow()) {
                btDelRow.isEnabled = true
            }
        }
        btDelRow.setOnClickListener {
            btDelRow.isEnabled = false
            viewModel.setDelRowNull()
            binding.setTheatreEdtDelRow.text = null
        }

        //添加col相关
        val btAddCol = binding.setTheatreBtAddColumn
        binding.setTheatreEdtAddColumn.afterTextChanged {
            viewModel.setAddCol(it)
        }
        viewModel.addCol.observe(this) {
            btAddCol.isEnabled = false
            if (viewModel.checkAddCol()) {
                btAddCol.isEnabled = true
            }
        }
        btAddCol.setOnClickListener {
            btAddCol.isEnabled = false
            viewModel.setAddColNull()
            binding.setTheatreEdtAddColumn.text = null
        }

        //删除col相关
        val btDelCol = binding.setTheatreBtDelColumn
        binding.setTheatreEdtDelColumn.afterTextChanged {
            viewModel.setDelCol(it)
        }
        viewModel.delCol.observe(this) {
            btDelCol.isEnabled = false
            if (viewModel.checkDelCol()) {
                btDelCol.isEnabled = true
            }
        }
        btDelCol.setOnClickListener {
            btDelCol.isEnabled = false
            viewModel.setDelColNull()
            binding.setTheatreEdtDelColumn.text = null
        }

        //座位相关
        val theatre = viewModel.theatre.value ?: TheatreData()
        viewModel.seatMessage.observe(this) {
            Log.d("TAG", "onCreate: ${it.size}")
            initSeatTable(seatTable, theatre, it)
        }


        val btDelSeat = binding.setTheatreBtUselessSeat
        val btIsSold = binding.setTheatreBtIsSoldSeat
        val seatData = viewModel.seatData
        seatData.observe(this) {
            btDelSeat.isEnabled = false
            btIsSold.isEnabled = false
            if (viewModel.checkList()) {
                btDelSeat.isEnabled = true
                btIsSold.isEnabled = true
            }
        }
        btDelSeat.setOnClickListener {
            btDelSeat.isEnabled = false
            btIsSold.isEnabled = false
            seatData.value?.let {
                viewModel.uploadSeat(it, 2)
            }
            seatTable.restart()
        }

        btIsSold.setOnClickListener {
            btIsSold.isEnabled = false
            btDelSeat.isEnabled = false
            seatData.value?.let {
                viewModel.uploadSeat(it, 0)
            }
            seatTable.restart()
        }

        //添加座位相关
        val btAddSeat = binding.setTheatreBtAddSeat
        val addSeatRow = binding.setTheatreEdtSeatRow
        val addSeatCol = binding.setTheatreEdtSeatColumn
        addSeatCol.afterTextChanged {
            viewModel.setRowAndCol(addSeatRow.text.toString(), addSeatCol.text.toString())
        }
        addSeatRow.afterTextChanged {
            viewModel.setRowAndCol(addSeatRow.text.toString(), addSeatCol.text.toString())
        }
        viewModel.addSeat.observe(this) {
            btAddSeat.isEnabled = false
            if (viewModel.checkAddSeat()) {
                btAddSeat.isEnabled = true
            }
        }
        btAddSeat.setOnClickListener {
            btAddSeat.isEnabled = false
            viewModel.setAddSeatNull()
            addSeatRow.text = null
            addSeatCol.text = null
        }
    }

    //初始化控件
    private fun initSeatTable(
        seatTableView: SeatTable,
        theatre: TheatreData,
        map: Map<Int, SeatData>,
    ) {
        val maxSelect = 6
        seatTableView.setScreenName(theatre.name) //设置屏幕名称

        seatTableView.setMaxSelected(maxSelect) //设置最多选中

        seatTableView.setSeatChecker(object : SeatTable.SeatChecker {
            //true为显示
            override fun isValidSeat(row: Int, column: Int): Boolean {
                val seat = SeatTheatreUtils.getSeatByRC(map, row + 1, column + 1, theatre)
                return seat.status != 2
            }

            override fun isSold(row: Int, column: Int): Boolean {
                val seat = SeatTheatreUtils.getSeatByRC(map, row + 1, column + 1, theatre)
                return seat.status != 1
            }

            override fun checked(row: Int, column: Int) {
                val seat = SeatTheatreUtils.getSeatByRC(map, row + 1, column + 1, theatre)
                viewModel.addSeat(seat)
            }

            override fun unCheck(row: Int, column: Int) {
                val seat = SeatTheatreUtils.getSeatByRC(map, row + 1, column + 1, theatre)
                viewModel.delSeat(seat)
            }

            override fun checkedSeatTxt(row: Int, column: Int): Array<String> {
                return arrayOf("")
            }
        })
        seatTableView.setData(theatre.row, theatre.col)

    }

}
package com.example.ttms.ui.addplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.ttms.R
import com.example.ttms.databinding.ActivityAddPlanMessageBinding
import com.example.ttms.ui.loginpackage.ui.login.afterTextChanged

class AddPlanMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlanMessageBinding
    private val viewModel: AddPlanMessageAcVm by lazy {
        ViewModelProvider(this)[AddPlanMessageAcVm::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlanMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setData(intent)

        val ticket = binding.addPlanMessageEdTicket
        val startTime = binding.addPlanMessageEdStartTime
        val endTime = binding.addPlanMessageEdSumTime

        ticket.afterTextChanged {
            viewModel.alterData(startTime.text.toString(),endTime.text.toString(),ticket.text.toString())
        }

        startTime.afterTextChanged {
            viewModel.alterData(startTime.text.toString(),endTime.text.toString(),ticket.text.toString())
        }

        endTime.afterTextChanged {
            viewModel.alterData(startTime.text.toString(),endTime.text.toString(),ticket.text.toString())
        }

        viewModel.data.observe(this){
            binding.addPlanMessageBtFinish.isEnabled = false
            if(viewModel.checkedBt()) {
                binding.addPlanMessageBtFinish.isEnabled = true
            }
        }

        binding.addPlanMessageBtFinish.setOnClickListener {
            viewModel.upload()
            finish()
        }
    }
}
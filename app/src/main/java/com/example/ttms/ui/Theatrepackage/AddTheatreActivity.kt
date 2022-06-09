package com.example.ttms.ui.Theatrepackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.ttms.databinding.ActivityAddTheatreBinding
import com.example.ttms.ui.loginpackage.ui.login.afterTextChanged
import com.example.ttms.util.ToastUtils

class AddTheatreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTheatreBinding
    private val viewModel: AddTheatreAcVm by lazy {
        ViewModelProvider(this)[AddTheatreAcVm::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTheatreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name = binding.addTheatreEdAddTheatreName
        val row = binding.addTheatreEdAddTheatreRow
        val column = binding.addTheatreEdAddTheatreCol
        val finish = binding.addTheatreBtFinish

        name.afterTextChanged {
            viewModel.theatreDataChanged(
                row.text.toString(),
                column.text.toString(),
                name.text.toString()
            )
        }

        column.afterTextChanged {
            viewModel.theatreDataChanged(
                row.text.toString(),
                column.text.toString(),
                name.text.toString()
            )
        }

        row.afterTextChanged {
            viewModel.theatreDataChanged(
                row.text.toString(),
                column.text.toString(),
                name.text.toString()
            )
        }

        finish.setOnClickListener {
            viewModel.uploadTheatre()
            finish.isEnabled = false
        }

        viewModel.result.observe(this) {
            if (it) {
                ToastUtils.toast(this, "上传成功！！")
                finish()
            }else {
                ToastUtils.toast(this, "上传失败！！")
                finish.isEnabled = true
            }
        }

        viewModel.message.observe(this) {
            binding.addTheatreBtFinish.isEnabled = false
            when (viewModel.isFinish()) {
                //0 -> ToastUtils.toast(this, "row或column不能小于等于0")
                //1 -> ToastUtils.toast(this, "名称不能为空")
                2 -> binding.addTheatreBtFinish.isEnabled = true
            }
            Log.d("TAG", "onCreate11: ")
        }

    }
}
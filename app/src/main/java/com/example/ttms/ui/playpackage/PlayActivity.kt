package com.example.ttms.ui.playpackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.ttms.databinding.ActivityPlayBinding
import com.example.ttms.util.StringUtils
import com.example.ttms.util.TimeUtils

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private val viewModel by lazy { ViewModelProvider(this)[PlayAcVm::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setDataSource(intent)

        viewModel.dataSource.observe(this) {
            binding.let { it1 ->
                it1.playAcTvTitle.text = it.title
                it1.playAcTvTime.text = TimeUtils.msToDay(it.releaseDate.toString())
                it1.playAcImHead.load(it.cover)
                it1.playAcTvType.text = StringUtils.listToBracket(it.type)
                it1.playAcTvLanguage.text = StringUtils.listToBracket(it.language)
                it1.playAcTvActor.text = StringUtils.listToBranch(it.actor, 8)
                it1.playAcTvRate.text = it.rate.toString()
                it1.playAcTvDescribe.text = it.introduction
                it1.playAcTvSumTime.text = it.filmlen.toString().plus("分钟")
            }
        }
    }


}
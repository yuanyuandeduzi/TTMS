package com.example.ttms.ui.addplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttms.R
import com.example.ttms.adapter.PlanSelectAcAdapter
import com.example.ttms.databinding.ActivityAddPlanSelectTheatreBinding
import com.example.ttms.databinding.ActivityPlanMeassageBinding

class AddPlanSelectTheatreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlanSelectTheatreBinding
    private val viewModel: AddPlanSelectTheatreAcVm by lazy {
        ViewModelProvider(this)[AddPlanSelectTheatreAcVm::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlanSelectTheatreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setMovieId(intent)

        viewModel.message.observe(this){
            val adapter = PlanSelectAcAdapter(it,viewModel.movieId)
            val linearManger = LinearLayoutManager(this)
            binding.addPlanAcSelectTheatreRc.also { it1->
                it1.adapter = adapter
                it1.layoutManager = linearManger
            }
        }

    }
}
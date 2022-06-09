package com.example.ttms.ui.addplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttms.R
import com.example.ttms.adapter.PlanMagAcAdapter
import com.example.ttms.databinding.ActivityAddPlanMessageBinding
import com.example.ttms.databinding.ActivityAddTheatreBinding
import com.example.ttms.databinding.ActivityPlanMeassageBinding
import com.example.ttms.ui.Theatrepackage.AddTheatreAcVm

class PlanMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlanMeassageBinding
    private val viewModel: PlanMessageAcVm by lazy {
        ViewModelProvider(this)[PlanMessageAcVm::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanMeassageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setMovieId(intent)

        viewModel.planMessage.observe(this) {
            binding.planMessageAcRc.also { it1->
                val adapter = viewModel.planMessage.value?.let { it2 -> PlanMagAcAdapter(it2) }
                val linearLayoutManager = LinearLayoutManager(this)
                it1.adapter = adapter
                it1.layoutManager = linearLayoutManager
            }
        }

        binding.theatreImBtToolbar.setOnClickListener{
            startActivity(Intent(this,AddPlanSelectTheatreActivity::class.java).also {
                it.putExtra("movieId", viewModel.movieId.value)
            })
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}
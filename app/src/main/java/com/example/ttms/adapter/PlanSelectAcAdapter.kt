package com.example.ttms.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ttms.R
import com.example.ttms.bean.TheatreData
import com.example.ttms.databinding.ItemRcTheatreBinding
import com.example.ttms.ui.addplan.AddPlanMessageActivity

class PlanSelectAcAdapter(val list: List<TheatreData>, val movieId: String?) :
    RecyclerView.Adapter<PlanSelectAcAdapter.ViewHolder>() {
    private lateinit var binding: ItemRcTheatreBinding

    inner class ViewHolder(binding: ItemRcTheatreBinding) : RecyclerView.ViewHolder(binding.root) {
        val content: TextView = binding.tvItemTheatre
        val layout = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRcTheatreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.also {
            it.content.text = data.name
            it.layout.setOnClickListener {
                binding.root.context.startActivity(Intent(binding.root.context,AddPlanMessageActivity::class.java).also { it1->
                    it1.putExtra("movieId",movieId)
                    it1.putExtra("theatreId",data.id.toString())
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
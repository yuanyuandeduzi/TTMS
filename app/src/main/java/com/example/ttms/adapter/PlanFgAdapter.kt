package com.example.ttms.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ttms.bean.DataSource
import com.example.ttms.databinding.ItemRcPlayBinding
import com.example.ttms.ui.addplan.PlanMessageActivity
import com.example.ttms.util.StringUtils
import com.example.ttms.util.TimeUtils

//item复用
class PlanFgAdapter(
    private var list: MutableList<DataSource>,val context : Context
) : RecyclerView.Adapter<PlanFgAdapter.ViewHolder>() {

    inner class ViewHolder(binding : ItemRcPlayBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.playTvTitle
        val im: ImageFilterView = binding.playIm1
        val describe: TextView = binding.playTvDescribe
        val time: TextView = binding.playTvTime
        val view = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRcPlayBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataSource = list[position]
        holder.also {
            it.im.load(dataSource.cover)
            it.time.text = TimeUtils.msToDay(dataSource.releaseDate.toString())
            it.describe.text = StringUtils.getStringByLength(dataSource.introduction, 28)
            it.title.text = StringUtils.getStringByLength(dataSource.title, 12)
        }

        holder.view.setOnClickListener {
            context.startActivity(Intent(context, PlanMessageActivity::class.java).also {
                Log.d("TAG", "onBindViewHolder: ${dataSource.mid}")
                it.putExtra("movieId", dataSource.mid.toString())
            })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
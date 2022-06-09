package com.example.ttms.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ttms.R
import com.example.ttms.bean.DataSource
import com.example.ttms.ui.playpackage.PlayActivity
import com.example.ttms.util.StringUtils
import com.example.ttms.util.TimeUtils

class PlayFgAdapter(
    private var list: MutableList<DataSource>, private val context: Context,
) : RecyclerView.Adapter<PlayFgAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.play_tv_title)
        val im: ImageFilterView = view.findViewById(R.id.play_im_1)
        val describe: TextView = view.findViewById(R.id.play_tv_describe)
        val time: TextView = view.findViewById(R.id.play_tv_time)
        val view = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rc_play, parent, false)
        return ViewHolder(view)
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
            context.startActivity(Intent(context,PlayActivity::class.java).also {
                it.putExtra("data",dataSource)
            })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
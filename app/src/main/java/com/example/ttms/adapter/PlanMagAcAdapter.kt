package com.example.ttms.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ttms.R
import com.example.ttms.bean.PlanData
import com.example.ttms.util.StringUtils
import com.example.ttms.util.TimeUtils

class PlanMagAcAdapter(val list : List<PlanData>) : RecyclerView.Adapter<PlanMagAcAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.item_plan_tv_title)
        val play : TextView = view.findViewById(R.id.item_plan_tv_play)
        val ticket : TextView = view.findViewById(R.id.item_plan_tv_ticket)
        val date : TextView = view.findViewById(R.id.item_plan_tv_date)
        val time : TextView = view.findViewById(R.id.item_plan_tv_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rc_plan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.also {
            Log.d("TAG", "onBindViewHolder: $it")
            it.title.text = StringUtils.getStringByLength(data.movieName,10)
            it.play.text = StringUtils.getStringByLength(data.studioName,10)
            it.time.text = TimeUtils.msToHour(data.startTime).plus("~").plus(TimeUtils.msToHour(data.endTime))
            it.ticket.text = data.ticketPrice.plus("元")
            it.date.text = TimeUtils.msToDay(data.startTime)
        }
    }

    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: ${list.size}")
        return list.size
    }

}
package com.example.ttms.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ttms.R
import com.example.ttms.fragment.TheatreFgVM
import com.example.ttms.ui.Theatrepackage.RecomposeTheatreActivity

class TheatreFgAdapter(
    private val viewModel: TheatreFgVM,
    private val context: Context,
) :
    RecyclerView.Adapter<TheatreFgAdapter.ViewHolder>() {

    private val mList = viewModel.message.value!!

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.tv_item_theatre)
        val delete: Button = view.findViewById(R.id.bt_item_theatre)
        val layout = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rc_theatre, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        holder.content.text = data.name
        holder.layout.setOnClickListener {
            if(holder.delete.visibility == View.VISIBLE) {
                holder.delete.visibility = View.INVISIBLE
            }else {
                //跳转设置座位活动
                context.startActivity(Intent(context,RecomposeTheatreActivity::class.java).also {
                    it.putExtra("id",data.id)
                    it.putExtra("col",data.col)
                    it.putExtra("row",data.row)
                    it.putExtra("status",data.status)
                    it.putExtra("name",data.name)
                })
            }
        }
        holder.layout.setOnLongClickListener {
            holder.delete.visibility = View.VISIBLE
            true
        }

        holder.delete.setOnClickListener {
            viewModel.removeByIndex(context,mList[position])
            holder.delete.visibility = View.INVISIBLE
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
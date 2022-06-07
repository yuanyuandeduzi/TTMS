package com.example.ttms.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ttms.R
import com.example.ttms.adapter.TheatreFgAdapter
import com.example.ttms.bean.TheatreData
import com.example.ttms.databinding.FragmentTheatreBinding
import com.example.ttms.ui.Theatre.AddTheatreActivity

class TheatreFragment : Fragment() {

    private lateinit var binding: FragmentTheatreBinding
    private val viewModel by lazy { ViewModelProvider(this)[TheatreFgVM::class.java] }
    private lateinit var adapter: TheatreFgAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheatreBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.theatreImBtToolbar.setOnClickListener {
            //跳转添加界面
            requireContext().startActivity(Intent(requireContext(),AddTheatreActivity::class.java))
        }
        //初始化RecyclerView
        viewModel.message.observe(viewLifecycleOwner) {
            adapter = TheatreFgAdapter(viewModel,requireContext())
            val linearManger = LinearLayoutManager(context)
            binding.theatreFragmentRc.also {
                it.layoutManager = linearManger
                it.adapter = adapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

}
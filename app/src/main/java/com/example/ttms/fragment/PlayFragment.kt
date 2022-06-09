package com.example.ttms.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ttms.adapter.PlayFgAdapter
import com.example.ttms.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[PlayAndPlanFgVm::class.java]
    }
    private var playAdapter: PlayFgAdapter? = null
    private lateinit var layManger: LinearLayoutManager

    private var isUseControl = false;
    private val run : Runnable = Runnable{
        smoothScrollToPosition(binding.playFragmentRc)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayBinding.inflate(inflater)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layManger = LinearLayoutManager(context)
        playAdapter = viewModel.dataSource.value?.let {
            PlayFgAdapter(it,requireContext())}
        binding.playFragmentRc.also {
            it.layoutManager = layManger
            it.adapter = playAdapter
        }

        viewModel.addNextPage()
        viewModel.dataSource.observe(viewLifecycleOwner) {
            Log.d("TAG", "onViewCreated: ")
            playAdapter?.notifyDataSetChanged()
        }
        binding.playFragmentRc.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.addNextPage()
                }

                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!isUseControl) {
                        recyclerView.postDelayed(run,200)
                    }
                }
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_SETTLING) {//非自动滑动
                    isUseControl = false;
                }
            }
        })

    }

    private fun smoothScrollToPosition(recyclerView : RecyclerView) {
        isUseControl = true
        val view = recyclerView.getChildAt(0)
        val height = view.measuredHeight
        val bottom = view.bottom
        if(bottom != height) {
            if(bottom >= (height / 2)) {
                recyclerView.smoothScrollBy(0,-(height - bottom + 10))
            }else{
                recyclerView.smoothScrollBy(0,bottom + 10)
            }
        }
    }


}
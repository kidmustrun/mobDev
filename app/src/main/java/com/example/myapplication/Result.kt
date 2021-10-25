package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ResultBinding

class Result : Fragment(){
    private lateinit var binding: ResultBinding
    private var x :Int = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = ResultBinding.inflate(layoutInflater)
        binding.resultOfOperation.text = Calculator.calculate().toString()
        val view = binding.root

        backButtonClick()
        return view
    }

    private fun backButtonClick(){
        binding.back.setOnClickListener {
            val activityCollback = requireActivity() as ActivityCollback
            activityCollback.showBackFragment(x)
        }
    }
}
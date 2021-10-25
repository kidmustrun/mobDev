package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.SecondNumBinding

class SecondNum : Fragment(){
    private lateinit var binding: SecondNumBinding
    private var x :Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = SecondNumBinding.inflate(layoutInflater)
        val view = binding.root
        nextButtonClick()
        backButtonClick()
        return view
    }

    private fun nextButtonClick(){
        binding.next.setOnClickListener{
            val activityCollback = requireActivity() as ActivityCollback
            Calculator.num2 = binding.number2.text.toString().toInt()
            activityCollback.showNextFragment(x)
        }
    }

    private fun backButtonClick(){
        binding.back.setOnClickListener {
            val activityCollback = requireActivity() as ActivityCollback
            activityCollback.showBackFragment(x)
        }
    }
}
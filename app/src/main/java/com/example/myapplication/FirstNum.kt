package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FirstNumBinding

class FirstNum : Fragment() {
    private lateinit var binding: FirstNumBinding
    private var x :Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FirstNumBinding.inflate(layoutInflater)
        val view = binding.root
        nextButtonClick()
        return view
    }

    private fun nextButtonClick(){
        binding.next.setOnClickListener{
            val activityCollback = requireActivity() as ActivityCollback
            Calculator.num1 = binding.number1.text.toString().toInt()
            activityCollback.showNextFragment(x)
        }
    }

}
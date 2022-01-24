package com.example.myapplication.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.DialogFragmentAddNodeBinding

class AddNodeDialogFragment(private val fireOnAdd: (Int) -> Unit) : DialogFragment() {

    private var _binding: DialogFragmentAddNodeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            _binding = DialogFragmentAddNodeBinding.inflate(LayoutInflater.from(context))
            builder.setView(binding.root)
                .setMessage("Add new Node: ")
                .setPositiveButton("Add") { _, _ ->
                    val valueInput = binding.nodeValue
                    try {
                        val value = valueInput.text.toString().toInt()
                        fireOnAdd(value)
                    } catch (exception: Throwable) {
                    }
                }
                .setNegativeButton(
                    "Cancel"
                ) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AddNodeDialog"
    }
}
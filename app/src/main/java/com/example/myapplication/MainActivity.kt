package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val verticalLinearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = verticalLinearLayoutManager
        binding.recyclerView.adapter =
            PersonAdapter(PersonList.createCollectionItems(), ::showCardMessage, ::showLikeMessage)
    }

    private fun showLikeMessage(person: Person) {
        val toast = Toast.makeText(applicationContext, "Нажат лайк:  " + person.name, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showCardMessage(person: Person) {
        val toast = Toast.makeText(applicationContext, "Нажата карточка:  " + person.name, Toast.LENGTH_SHORT)
        toast.show()
    }
}
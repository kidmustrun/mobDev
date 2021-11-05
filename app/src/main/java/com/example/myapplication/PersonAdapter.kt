package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.transform.RoundedCornersTransformation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBinding
import coil.load

class PersonAdapter(
    var listperson: List<Person>,
    private val clickCard: (Person) -> Unit,
    private val clickCardLike: (Person) -> Unit
): RecyclerView.Adapter<PersonAdapter.MyList>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyList {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyList(binding, clickCard, clickCardLike)
    }

    override fun onBindViewHolder(
        list: MyList,
        position: Int
    ) {
        val person = listperson[position]
        list.bind(person)
    }
    override fun getItemCount(): Int {
        return listperson.size
    }
    inner class MyList internal constructor(
        private val binding: ItemBinding,
        private val clickCard: (Person) -> Unit,
        private val clickCardLike: (Person) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) = binding.run{
            name.text = person.name
            sex.text = person.sex
            date.text = person.date
            text.text = person.text
            image.load(person.image){
                transformations(RoundedCornersTransformation(50f))
            }
            binding.card.setOnClickListener {
                clickCard.invoke(person)
            }
            binding.like.setOnClickListener {
                clickCardLike.invoke(person)
                binding.like.setImageResource(R.drawable.ic_like_pressed)
            }
        }
    }
}
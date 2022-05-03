package com.ubedaPablo.proyecto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ubedaPablo.proyecto.R
import com.ubedaPablo.proyecto.room.CharacterDnD

class CharacterRecyclerAdapter : RecyclerView.Adapter<CharacterRecyclerAdapter.ViewHolder>() {

    var characters: MutableList<CharacterDnD> = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(characters: MutableList<CharacterDnD>, context: Context) {
        this.characters = characters
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = characters.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_character_list, parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val charId = view.findViewById(R.id.txtId) as TextView
        val name = view.findViewById(R.id.txtName) as TextView
        val born = view.findViewById(R.id.txtBorn) as TextView
        val desc = view.findViewById(R.id.txtDesc) as TextView

        fun bind(character: CharacterDnD, context: Context) {
            charId.text = character.id.toString()
            name.text = character.name
            born.text = character.born
            desc.text = character.desc
            itemView.setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    context,
                    character.name,
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }
}
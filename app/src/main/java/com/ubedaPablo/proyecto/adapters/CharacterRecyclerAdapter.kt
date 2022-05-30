package com.ubedaPablo.proyecto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ubedaPablo.proyecto.R
import com.ubedaPablo.proyecto.room.CharacterDnD
import com.ubedaPablo.proyecto.ui.characters.CharacterFragment

class CharacterRecyclerAdapter : RecyclerView.Adapter<CharacterRecyclerAdapter.ViewHolder>() {

    var characters: MutableList<CharacterDnD> = ArrayList()
    lateinit var context: CharacterFragment

    fun RecyclerAdapter(characters: MutableList<CharacterDnD>, context: CharacterFragment) {
        this.characters = characters
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = characters[position]
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_character_list, parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        private lateinit var listener: CharacterAdapterListener

        interface CharacterAdapterListener {
            fun onLongClickCreateDialog(id: Int)
        }

        val charId = view.findViewById(R.id.txtId) as TextView
        val name = view.findViewById(R.id.txtName) as TextView
        val born = view.findViewById(R.id.txtBorn) as TextView
        val desc = view.findViewById(R.id.txtDesc) as TextView

        fun bind(character: CharacterDnD, context: CharacterFragment) {
            charId.text = character.id.toString()
            name.text = character.name
            born.text = character.born
            desc.text = character.desc

            itemView.setOnLongClickListener(this)
            itemView.setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    context.requireContext(),
                    character.name,
                    Toast.LENGTH_SHORT
                ).show()
            })

            try {
                listener = context
            } catch (e: ClassCastException) {
                throw ClassCastException(
                    ("$context must implement CharacterAdapterListener")
                )
            }
        }

        override fun onLongClick(p0: View?): Boolean {
            listener.onLongClickCreateDialog(charId.text.toString().toInt())
            return true
        }
    }
}
package com.ubedaPablo.proyecto.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ubedaPablo.proyecto.databinding.FragmentAddCharBinding
import com.ubedaPablo.proyecto.room.CharacterDYD
import com.ubedaPablo.proyecto.room.CharacterRoomDatabase


class AddCharFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAddCharBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCharBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        val dao = CharacterRoomDatabase.getDatabase(requireContext()).characterDao()
        dao.insertAll(generateCharacter())
        parentFragmentManager.popBackStack()
    }

    fun generateCharacter(): CharacterDYD {
        return CharacterDYD(
            name = binding.editTextName.text.toString(),
            born = binding.editTextDate.text.toString(),
            desc = binding.editTextDesc.text.toString()
        )
    }
}
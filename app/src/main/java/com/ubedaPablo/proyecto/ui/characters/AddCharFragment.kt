package com.ubedaPablo.proyecto.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ubedaPablo.proyecto.databinding.FragmentAddCharBinding
import com.ubedaPablo.proyecto.room.CharacterDnD
import com.ubedaPablo.proyecto.room.CharacterRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCharFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAddCharBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddCharBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        lifecycleScope.launch(Dispatchers.Main) {
            val dao = CharacterRoomDatabase.getDatabase(requireContext()).characterDao()
            dao.insertAll(generateCharacter())
            parentFragmentManager.popBackStack()
        }
    }

    private fun generateCharacter(): CharacterDnD {
        return CharacterDnD(
            name = binding.editTextName.text.toString(),
            born = binding.editTextDate.text.toString(),
            desc = binding.editTextDesc.text.toString()
        )
    }
}
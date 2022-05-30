package com.ubedaPablo.proyecto.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ubedaPablo.proyecto.databinding.FragmentCharSheetBinding
import com.ubedaPablo.proyecto.room.CharacterDnD
import com.ubedaPablo.proyecto.room.CharacterRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharSheetFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCharSheetBinding? = null
    private val binding get() = _binding!!
    private var edit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharSheetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bundle = requireArguments()
        if (bundle.getBoolean("Edit")) {
            edit = true
            val dao = CharacterRoomDatabase.getDatabase(requireContext()).characterDao()
            lifecycleScope.launch(Dispatchers.IO) {
                val characterToEdit = dao.getOne(bundle.getInt("Id"))

                withContext(Dispatchers.Main) {
                    binding.editTextName.setText(characterToEdit.name)
                    binding.editTextDate.setText(characterToEdit.born)
                    binding.editTextDesc.setText(characterToEdit.desc)
                }
            }
        }

        binding.button.setOnClickListener(this)
        return root
    }

    override fun onClick(p0: View?) {
        lifecycleScope.launch(Dispatchers.Main) {
            val dao = CharacterRoomDatabase.getDatabase(requireContext()).characterDao()
            if (edit) {
                dao.updateAll(generateCharacter())
            } else {
                dao.insertAll(generateCharacter())
            }
            parentFragmentManager.popBackStack()
        }
    }

    private fun generateCharacter(): CharacterDnD {
        return if (edit) {
            CharacterDnD(
                id  = requireArguments().getInt("Id"),
                name = binding.editTextName.text.toString(),
                born = binding.editTextDate.text.toString(),
                desc = binding.editTextDesc.text.toString()
            )
        } else {
            CharacterDnD(
                name = binding.editTextName.text.toString(),
                born = binding.editTextDate.text.toString(),
                desc = binding.editTextDesc.text.toString()
            )
        }
    }
}
package com.ubedaPablo.proyecto.ui.characters

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubedaPablo.proyecto.R
import com.ubedaPablo.proyecto.adapters.CharacterRecyclerAdapter
import com.ubedaPablo.proyecto.databinding.FragmentCharacterBinding
import com.ubedaPablo.proyecto.room.CharacterRoomDatabase

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)
        activity?.invalidateOptionsMenu()

        loadRecyclerView()
        return root
    }

    private fun loadRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val dao = CharacterRoomDatabase.getDatabase(requireContext()).characterDao()
        val characterList = dao.getAll()
        val adapter = CharacterRecyclerAdapter()
        adapter.RecyclerAdapter(characterList.toMutableList(), requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return when (item.itemId) {
            R.id.add_character -> {
                navController.navigate(R.id.addCharFragment)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.add_character).isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
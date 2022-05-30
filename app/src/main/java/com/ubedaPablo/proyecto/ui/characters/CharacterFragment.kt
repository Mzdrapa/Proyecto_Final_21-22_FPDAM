package com.ubedaPablo.proyecto.ui.characters

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubedaPablo.proyecto.EditDeleteDialog
import com.ubedaPablo.proyecto.R
import com.ubedaPablo.proyecto.adapters.CharacterRecyclerAdapter
import com.ubedaPablo.proyecto.databinding.FragmentCharacterBinding
import com.ubedaPablo.proyecto.room.CharacterRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterFragment : Fragment(), EditDeleteDialog.DialogListener,
    CharacterRecyclerAdapter.ViewHolder.CharacterAdapterListener {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private val adapter = CharacterRecyclerAdapter()
    private var idToModify: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)
        activity?.invalidateOptionsMenu()

        loadRecyclerView()
        return root
    }

    private fun loadRecyclerView() {
        val dao = CharacterRoomDatabase.getDatabase(requireContext()).characterDao()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch(Dispatchers.IO) {
            val characterList = dao.getAll()
            adapter.RecyclerAdapter(characterList.toMutableList(), this@CharacterFragment)
            withContext(Dispatchers.Main) {
                binding.recyclerView.adapter = adapter
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return when (item.itemId) {
            R.id.add_character -> {
                val bundle = Bundle()
                bundle.putBoolean("Edit", false)
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

    override fun onLongClickCreateDialog(id: Int) {
        idToModify = id
        val dialogo = EditDeleteDialog()
        dialogo.setListener(this)
        dialogo.show(childFragmentManager, "Edit or Delete Dialog")
    }

    override fun onEditClick() {
        val bundle = Bundle()
        bundle.putBoolean("Edit", true)
        bundle.putInt("Id", idToModify)
        val navController = findNavController()
        navController.navigate(R.id.addCharFragment, bundle)
    }

    override fun onDeleteClick() {
        val dao = CharacterRoomDatabase.getDatabase(requireContext()).characterDao()
        lifecycleScope.launch(Dispatchers.IO) {
            dao.delete(dao.getOne(idToModify))
            withContext(Dispatchers.Main) { loadRecyclerView() }
        }
    }
}
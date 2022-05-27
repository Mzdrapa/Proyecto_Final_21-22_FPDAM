package com.ubedaPablo.proyecto.ui.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubedaPablo.proyecto.adapters.RulesRecyclerAdapter
import com.ubedaPablo.proyecto.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadRecyclerView()

        return root
    }

    private fun loadRecyclerView() {
        binding.rulesRecyclerView.layoutManager = LinearLayoutManager(context)
        val filesList = requireContext().assets.list("rulesPDF")
        val adapter = RulesRecyclerAdapter()
        if (filesList != null) {
            adapter.rulesRecyclerAdapterBuilder(
                filesList.toMutableList(),
                requireContext()
            )
        }
        binding.rulesRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
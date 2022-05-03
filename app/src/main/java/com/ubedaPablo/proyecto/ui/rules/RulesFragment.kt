package com.ubedaPablo.proyecto.ui.rules

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubedaPablo.proyecto.adapters.RulesRecyclerAdapter
import com.ubedaPablo.proyecto.databinding.FragmentRulesBinding

class RulesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //checkPermi()
        loadRecyclerView()

        return root
    }
    private fun loadRecyclerView() {
        binding.rulesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.rulesRecyclerView.setOnClickListener(this)
        val filesList = requireContext().assets.list("rulesPDF")
        val adapter = RulesRecyclerAdapter()
        if (filesList != null) {
            adapter.RulesRecyclerAdapter(filesList.toMutableList(), requireContext())
        }
        binding.rulesRecyclerView.adapter = adapter
    }
    override fun onClick(p0: View?) {
        //TODO permisos
    }

    fun checkPermi() {
        val permissionWrite =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            Log.i("File", "Permission to record denied")
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("File", "Permission has been denied")
                } else {
                    Log.i("File", "Permission has been granted")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
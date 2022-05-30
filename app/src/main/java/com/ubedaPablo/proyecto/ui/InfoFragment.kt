package com.ubedaPablo.proyecto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ubedaPablo.proyecto.R
import com.ubedaPablo.proyecto.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)


        val bundle = requireArguments()
        if (bundle.getBoolean("FirstOpen")) {
            binding.textView.text = getString(R.string.firstOpen)
        }
        return binding.root
    }

}
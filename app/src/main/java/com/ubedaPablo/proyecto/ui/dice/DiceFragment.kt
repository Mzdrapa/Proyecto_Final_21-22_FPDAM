package com.ubedaPablo.proyecto.ui.dice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ubedaPablo.proyecto.Dice
import com.ubedaPablo.proyecto.databinding.FragmentDiceBinding

class DiceFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDiceBinding? = null

    private val binding get() = _binding!!
    private lateinit var dice: Dice

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDiceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnD20.setOnClickListener(this)
        binding.btnD12.setOnClickListener(this)
        binding.btnD10.setOnClickListener(this)
        binding.btnD8.setOnClickListener(this)
        binding.btnD6.setOnClickListener(this)
        binding.btnD4.setOnClickListener(this)

        dice = Dice(binding.txtNum, binding.imgDice, requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            var type = 20
            when (p0.id) {
                binding.btnD20.id -> {
                    type = 20
                }
                binding.btnD12.id -> {
                    type = 12
                }
                binding.btnD10.id -> {
                    type = 10
                }
                binding.btnD8.id -> {
                    type = 8
                }
                binding.btnD6.id -> {
                    type = 6
                }
                binding.btnD4.id -> {
                    type = 4
                }
            }
            dice.diceClicked(type)
        }
    }

}
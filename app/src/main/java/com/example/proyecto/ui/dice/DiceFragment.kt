package com.example.proyecto.ui.dice

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto.Dice
import com.example.proyecto.R
import com.example.proyecto.databinding.FragmentDiceBinding
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random

class DiceFragment : Fragment(), View.OnClickListener {

    private lateinit var diceViewModel: DiceViewModel
    private var _binding: FragmentDiceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dado: Dice

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        diceViewModel =
            ViewModelProvider(this).get(DiceViewModel::class.java)

        _binding = FragmentDiceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnD20.setOnClickListener(this)
        binding.btnD12.setOnClickListener(this)
        binding.btnD10.setOnClickListener(this)
        binding.btnD8.setOnClickListener(this)
        binding.btnD6.setOnClickListener(this)
        binding.btnD4.setOnClickListener(this)

        dado = Dice()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            var resource: Int = 0
            when (p0.id) {
                binding.btnD20.id -> {
                    dado.tipo = "D20"
                    resource = R.drawable.ic_d20
                }
                binding.btnD12.id -> {
                    dado.tipo = "D12"
                    resource = R.drawable.ic_d12
                }
                binding.btnD10.id -> {
                    dado.tipo = "D10"
                    resource = R.drawable.ic_d10
                }
                binding.btnD8.id -> {
                    dado.tipo = "D8"
                    resource = R.drawable.ic_d8
                }
                binding.btnD6.id -> {
                    dado.tipo = "D6"
                    resource = R.drawable.ic_d6
                }
                binding.btnD4.id -> {
                    dado.tipo = "D4"
                    resource = R.drawable.ic_d4
                }
            }
            Log.i("Image", "Image changed to " + resources.getResourceName(resource))
            if (resource != 0) {
                binding.imgDice.setImageResource(resource)
                var count = 0
                val end = Random.nextInt(10,30)
                Timer().scheduleAtFixedRate(0, 100) {
                    activity?.runOnUiThread {
                        val num = Random.nextInt(1, 21).toString()
                        binding.txtNum.text = num
                        Log.d("Timer", num)
                    }
                    if(++count >= end) cancel()
                }
                dado.lanzarDado()
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
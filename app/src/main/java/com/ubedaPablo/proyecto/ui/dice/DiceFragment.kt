package com.ubedaPablo.proyecto.ui.dice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ubedaPablo.proyecto.Dice
import com.ubedaPablo.proyecto.R
import com.ubedaPablo.proyecto.databinding.FragmentDiceBinding
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random

class DiceFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDiceBinding? = null

    private val binding get() = _binding!!
    private var dado: Dice = Dice()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDiceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnD20.setOnClickListener(this)
        binding.btnD12.setOnClickListener(this)
        binding.btnD10.setOnClickListener(this)
        binding.btnD8.setOnClickListener(this)
        binding.btnD6.setOnClickListener(this)
        binding.btnD4.setOnClickListener(this)

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
                val end = Random.nextInt(10, 30)
                Timer().scheduleAtFixedRate(0, 100) {
                    activity?.runOnUiThread {
                        val num =
                            Random.nextInt(1, dado.tipo.removePrefix("D").toInt() + 1).toString()
                        binding.txtNum.text = num
                        Log.d("Timer", num)
                    }
                    if (++count >= end) cancel()
                }
                //dado.lanzarDado()
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
package com.ubedaPablo.proyecto

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.math.roundToInt
import kotlin.random.Random

data class Dice(val text: TextView, val img: ImageView, val context: Context) {
    var image: Int = R.drawable.ic_d20
    var maxValueOfDice: Int = 20

    fun diceClicked(type: Int) {
        when (type) {
            20 -> {
                maxValueOfDice = 20
                image = R.drawable.ic_d20
            }
            12 -> {
                maxValueOfDice = 12
                image = R.drawable.ic_d12
            }
            10 -> {
                maxValueOfDice = 10
                image = R.drawable.ic_d10
            }
            8 -> {
                maxValueOfDice = 8
                image = R.drawable.ic_d8
            }
            6 -> {
                maxValueOfDice = 6
                image = R.drawable.ic_d6
            }
            4 -> {
                maxValueOfDice = 4
                image = R.drawable.ic_d4
            }
        }
        drawImage()
        generateRandomNumbers()
    }

    private fun generateRandomNumbers() {
        var numbersGenerated = 0
        val numbersTried = Random.nextInt(maxValueOfDice, maxValueOfDice.times(1.5).roundToInt())
        Timer().scheduleAtFixedRate(0, 100) {
            Handler(Looper.getMainLooper()).post {
                val num = Random.nextInt(1, maxValueOfDice + 1).toString()
                text.text = num
                if (++numbersGenerated >= numbersTried) cancel()

            }
        }
    }

    private fun drawImage() {
        img.setImageResource(image)
    }
}
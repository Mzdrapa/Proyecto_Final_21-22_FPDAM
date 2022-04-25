package com.example.proyecto

import android.graphics.drawable.Drawable
import kotlin.random.Random

class Dice(var tipo: String = "D20") {
    lateinit var drawable: Drawable
    var num: Int = 0
    fun lanzarDado() {
        num = Random.nextInt(tipo.removePrefix("D").toInt())

    }
}
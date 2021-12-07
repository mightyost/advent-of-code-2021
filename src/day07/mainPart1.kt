package day07p1

import readTextInput
import java.lang.Math.abs

fun main() {

//    val data = readData("day07/data_test")
    val data = readData("day07/data")

    val positions = IntArray(data.size) { it }

    val min = positions.minOf { calcFuel(data, it) }

    println("Min: $min")

    /*
    Min: 352331
     */
}

fun readData(filename: String): List<Int> =
    readTextInput(filename).split(",").map { it.toInt() }

fun calcFuel(positions: List<Int>, position: Int): Int =
    positions.map { abs(it - position) }.sum()

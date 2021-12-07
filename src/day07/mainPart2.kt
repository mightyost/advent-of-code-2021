package day07p2

import readTextInput
import java.lang.Math.abs

fun main() {

//    val data = readData("day07/data_test")
    val data = readData("day07/data")

    val positions = IntArray(data.size) { it }

    val min = positions.minOf { calcFuel(data, it) }

    println("Min: $min")

    /*
    Min: 99266250
     */
}

fun readData(filename: String): List<Int> =
    readTextInput(filename).split(",").map { it.toInt() }

fun calcFuel(positions: List<Int>, position: Int): Int =
    positions.map { cost(abs(it - position)) }.sum()

fun cost(distance: Int): Int =
    distance * (distance + 1) / 2

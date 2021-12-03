package day03p1

import readInput
import kotlin.math.pow

fun main() {

    //    val data = readInput("day03/data_test")
    val data = readInput("day03/data")

    val length = data[0].length

    val ones = IntArray(length)

    for (value in data) {
        for (i in 0 until value.length) {
            if (value[i] == '1') {
                ones[i] += 1
            }
        }
    }

    var gamma = 0

    for (count in ones) {
        gamma = gamma shl 1
        if (count > data.size/2) {
            gamma += 1
        }
    }

    val epsilon = 2.0.pow(length).toInt() -1  - gamma

    println("Gamma: ${gamma.toBinString()}, Epsilon: ${epsilon.toBinString()}")

    println(gamma * epsilon)

    /*
    Gamma: 110100010101, Epsilon: 1011101010
    2498354
     */
}

fun Int.toBinString() = this.toString(radix = 2)

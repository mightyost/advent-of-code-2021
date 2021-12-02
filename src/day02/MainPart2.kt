package day02

import readInput

fun main() {

//    val commands = readInput("day02/data_test")
    val commands = readInput("day02/data")

    var aim = 0
    var depth = 0
    var horizontal = 0

    for (command in commands) {

        val strings = command.split(" ")
        val amount = strings[1].toInt()
        when (strings[0]) {
            "up" -> aim -= amount
            "down" -> aim += amount

            "forward" -> {
                horizontal += amount
                depth += aim * amount
            }

            else -> throw IllegalArgumentException(command)
        }
    }

    val product = horizontal * depth

    println("aim: $aim")
    println("horizontal: $horizontal")
    println("depth: $depth")
    println("product: $product")
}



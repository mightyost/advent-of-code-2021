package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {

        var position = Position(0, 0)

        for (command in input) {
            val movement = command.movement()

            position += movement
        }

        return position.forward * position.depth
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("day02/data_test")
    println(part1(testInput))

    val input = readInput("day02/data")
    println(part1(input))
}

fun String.movement(): Position {
    val strings = this.split(" ")
    val distance = strings[1].toInt()
    return when (strings[0]) {
        "forward" -> Position(distance, 0)
        "up" -> Position(0, -distance)
        "down" -> Position(0, distance)
        else -> throw IllegalArgumentException(this)
    }
}

class Position(var forward: Int, var depth : Int) {
    operator fun plus(position: Position): Position =
        Position(
            forward = forward + position.forward,
            depth = depth + position.depth
        )
}



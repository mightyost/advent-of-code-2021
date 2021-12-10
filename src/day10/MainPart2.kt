package day10p2

import readInput

fun main() {

//    val data = readInput("day10/data_test")
    val data = readInput("day10/data")

    val scores = mutableListOf<Long>()

    for (line in data) {

        val stack = ArrayDeque<Char>()
        var isError = false

        for (char in line) {

            if (char.isOpening()) {
                stack.addFirst(char)
                continue
            }

            val opener = stack.removeFirst()

            if (!opener.isCorrectClosing(char)) {
                isError = true
                break
            }
        }

        if (!isError) {
            var score = 0L
            stack.forEach {
                score = score*5 + it.points()
            }
            scores += score
        }
    }

    val result = scores.sorted()[scores.size/2]

    println("Result: $result")

    /*
    Result: 2165057169
    */
}

fun Char.isOpening() = "([{<".contains(this)

fun Char.isCorrectClosing(char: Char) = when (char) {
    ')' -> this == '('
    ']' -> this == '['
    '}' -> this == '{'
    '>' -> this == '<'
    else -> throw IllegalArgumentException()
}

fun Char.points() = when(this) {
    '(' -> 1
    '[' -> 2
    '{' -> 3
    '<' -> 4
    else -> throw IllegalArgumentException()
}

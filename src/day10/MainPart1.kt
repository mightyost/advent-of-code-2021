package day10p1

import readInput

fun main() {

//    val data = readInput("day10/data_test")
    val data = readInput("day10/data")

    val stack = ArrayDeque<Char>()

    val errors = mutableMapOf(
        ')' to 0,
        ']' to 0,
        '}' to 0,
        '>' to 0
    )

    for (line in data) {
        for (char in line) {

            if (char.isOpening()) {
                stack.addFirst(char)
                continue
            }

            val opener = stack.removeFirst()

            if (!opener.isCorrectClosing(char)) {
                errors[char] = errors[char]!! + 1
                break
            }
        }
    }

    val score = errors.map { it.key.points() * it.value }.sum()

    println("Score: $score")

    /*
    Score: 216297
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
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> throw IllegalArgumentException()
}

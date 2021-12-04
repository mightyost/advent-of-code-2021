package day04p2

import readInput
import java.util.regex.Pattern

fun main() {

//    val data = readInput("day04/data_test")
    val data = readInput("day04/data")

    val draws = data[0].split(",").map { it.toInt() }

    val boards = mutableListOf<Board>()
    var row = 2

    while (row < data.size) {
        val rows = mutableListOf<String>()
        for (i in row until row + 5) {
            rows += data[i]
        }
        boards += Board(rows)
        row += 6
    }

    for (number in draws) {
        println("Draw: $number")
        val winners = mutableListOf<Board>()
        for (board in boards) {
            val bingo = board.mark(number)

            if (bingo) {
                winners += board
            }
        }
        boards -= winners

        if (boards.isEmpty()) {
            val board = winners[0]
            val sum = board.sum()
            println("Loser:\n$board")
            println("Sum: $sum * $number = ${sum * number}")
            return

        }
    }

    /*
    Loser:
     8 34 81 67 80     * * * * *
    83 92 13 11 41     . * * * *
    39 89 93 49 43     * . . * *
    20 69  3 74 76     * * * . *
    44 72 68 70 45     * . * . .

    Sum: 526 * 34 = 17884
    */
}

class Board(data: List<String>) {
    val board = Array(5) { Array(5) { 0 } }
    val marks = Array(5) { Array(5) { false } }

    init {
        for (y in 0..4) {
            val numbers = data[y].split(" ").filter{ !it.isBlank() }.map { it.toInt() }
            for (x in 0..4) {
                board[x][y] = numbers[x]
            }
        }
    }

    fun mark(number: Int): Boolean {
        for (y in 0..4) {
            for (x in 0..4) {
                if (board[x][y] == number) {
                    marks[x][y] = true
                }
            }
        }
        return isBingo()
    }

    fun isBingo(): Boolean {

        for (y in 0..4) {
            for (x in 0..4) {
                if (!marks[x][y]) {
                    break
                }
                if (x == 4) {
                    return true
                }
            }
        }
        for (x in 0..4) {
            for (y in 0..4) {
                if (!marks[x][y]) {
                    break
                }
                if (y == 4) {
                    return true
                }
            }
        }
        return false
    }

    fun sum(): Int {
        var sum = 0
        for (y in 0..4) {
            for (x in 0..4) {
                if (!marks[x][y]) {
                    sum += board[x][y]
                }
            }
        }
        return sum
    }

    override fun toString(): String {
        var txt = ""

        for (y in 0..4) {
            for (x in 0..4) {
                if (board[x][y] < 10) {
                    txt += " "
                }
                txt += "${board[x][y]} "
            }
            txt += "    "
            for (x in 0..4) {
                val mark = if (marks[x][y]) "*" else "."
                txt += "$mark "
            }
            txt += "\n"
        }
        return txt
    }
}
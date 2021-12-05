package day05p2

import readInput
import java.lang.Integer.max
import java.lang.Math.abs

fun main() {

//    val data = readInput("day05/data_test")
    val data = readInput("day05/data")

    val lines = data.map { Line.from(it) }

    val W = lines.maxOf { max(it.x1, it.x2) }
    val H = lines.maxOf { max(it.y1, it.y2) }

    val board = Board(W, H)

    lines.forEach { board.draw(it) }

//    println(board)
    println("Intersections: ${board.nbrOfIntersections}")
}

class Line(
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int
) {
    companion object {
        // 582,570 -> 582,313
        fun from(input: String): Line {
            val parts = input.split(" -> ", ",").map { it.toInt() }
            return Line(parts[0], parts[1], parts[2], parts[3])
        }
    }

    val length get() = max(abs(x1 - x2), abs(y1 - y2))
}

class Board(width: Int, height: Int) {
    val board = Array(width+1) { Array(height+1) { 0 } }

    val nbrOfIntersections get() = board.flatten().filter { it > 1 }.count()

    fun draw(line: Line) {

        val dx = if (line.x2 == line.x1) 0 else if (line.x2 > line.x1) 1 else -1
        val dy = if (line.y2 == line.y1) 0 else if (line.y2 > line.y1) 1 else -1

        for (i in 0..line.length) {
            board[line.x1 + i * dx][line.y1 + i * dy] += 1
        }
    }

    override fun toString(): String {

        val txt = StringBuilder()

        for (y in 0 until board[0].size) {
            for (x in 0 until board.size) {
                txt.append(if (board[x][y] == 0) "." else "${board[x][y]}")
            }
            txt.append("\n")
        }

        return txt.toString()
    }
}
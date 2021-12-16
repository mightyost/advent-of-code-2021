package day15p1

import readInput
import java.util.*
import kotlin.math.min

data class Pos(
    val x: Int,
    val y: Int
)

data class Node(
    val pos: Pos,
    val risk: Int
)

private class NodeComparator : Comparator<Node> {
    override fun compare(first: Node, second: Node): Int {
        return first.risk.compareTo(second.risk)
    }
}

fun main() {

//    val cave = readData("day15/data_test")
    val cave = readData("day15/data")

    val visited = Array(cave.size) { Array(cave[0].size) { -1 } }

    var minRisk = Int.MAX_VALUE

    val stack = PriorityQueue(NodeComparator())

    stack.add(Node(Pos(0, 0), 0))

    while(stack.isNotEmpty()) {
        val current = stack.remove()

        // Hit a wall?
        if (cave.outOfBounds(current.pos)) {
            continue
        }

        // Calc total risk for route
        val risk = current.risk + cave.risk(current.pos)

        if (risk >= minRisk) {
            continue
        }

        // If already visited and this route is riskier...
        if (visited.getTotalRisk(current.pos) > 0 && risk >= visited.getTotalRisk(current.pos)) {
            continue
        }

        // We found a better route to this pos
        visited.setTotalRisk(current.pos, risk)

        // Have we found a route to the exit?
        if (cave.isExit(current.pos)) {
            minRisk = min(minRisk, risk)
            println("Risk: $minRisk, size: ${stack.size}")
            continue
        }

        // Visit next...
        stack.add(Node(Pos(current.pos.x + 1, current.pos.y + 0), risk))
        stack.add(Node(Pos(current.pos.x - 1, current.pos.y + 0), risk))
        stack.add(Node(Pos(current.pos.x + 0, current.pos.y + 1), risk))
        stack.add(Node(Pos(current.pos.x + 0, current.pos.y - 1), risk))
    }

    println("Risk: $minRisk")

    /*
    Risk: 811
    */
}

fun readData(name: String): List<List<Int>> = readInput(name).map { it.toCharArray().map { it.digitToInt() } }

fun List<List<Int>>.risk(pos: Pos): Int = if (pos.x == 0 && pos.y == 0) 0 else this[pos.y][pos.x]

fun List<List<Int>>.outOfBounds(pos: Pos): Boolean = pos.x < 0 || pos.y < 0 || pos.x >= this[0].size || pos.y >= this.size

fun List<List<Int>>.isExit(pos: Pos): Boolean = pos.x == this[0].size-1 && pos.y == this.size-1

fun Array<Array<Int>>.getTotalRisk(pos: Pos) = this[pos.y][pos.x]
fun Array<Array<Int>>.setTotalRisk(pos: Pos, risk: Int) { this[pos.y][pos.x] = risk }

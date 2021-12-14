package day14p1

import readInput

fun main() {

//    val name = "day14/data_test"
    val name = "day14/data"

    var formula = readFormula(name)
    val rules = readRules(name)

    val steps = 10

    for (step in 1..steps) {

        var polymer = ""

        for (i in 0 until formula.length - 1) {
            val pair = formula.substring(i, i+2)

            polymer += pair[0] + rules[pair]!!
        }

        polymer += formula.last()

        formula = polymer
    }

    val sizes = formula.toCharArray().groupBy { it }.map { it.key to it.value.size }

    val min = sizes.minOf { it.second }
    val max = sizes.maxOf { it.second }

    println("Max: $max, min: $min -> ${max - min}")

    // Max: 3950, min: 644 -> 3306
}

fun readFormula(name: String): String = readInput(name)[0]
fun readRules(name: String): Map<String, String> = readInput(name)
    .drop(2)
    .map { it.split(" -> ") }
    .map { it[0] to it[1] }
    .toMap()

package day14p2

import readInput

fun main() {

//    val name = "day14/data_test"
    val name = "day14/data"

    val formula = readFormula(name)
    val rules = readRules(name)

    var counters = mutableMapOf<String, Long>()

    // Count initial pairs

    formula.windowed(2).forEach { pair ->
        counters[pair] = counters.getOrDefault(pair, 0) + 1
    }

    val steps = 40

    // Count rest
    repeat (steps) {

        val newCounters = mutableMapOf<String, Long>()

        for (pair in counters.keys) {
            val char = rules[pair]
            val first = "${pair[0]}$char"
            val second = "$char${pair[1]}"

            newCounters[first] = newCounters.getOrDefault(first, 0) + counters[pair]!!
            newCounters[second] = newCounters.getOrDefault(second, 0) + counters[pair]!!
        }
        counters = newCounters
    }

    val mapOfCharAndCount = mutableMapOf<Char, Long>()
    counters.forEach { (key, value) ->
        mapOfCharAndCount[key[1]] = mapOfCharAndCount.getOrDefault(key[1], 0) + value
    }
    mapOfCharAndCount[counters.keys.first()[0]] =
        mapOfCharAndCount.getOrDefault(counters.keys.first()[0], 0) + counters.values.first()

    val min = mapOfCharAndCount.values.minOrNull()!!
    val max = mapOfCharAndCount.values.maxOrNull()!!

    println("Max: $max, min: $min -> ${max - min}")

    // Max: 4439532321735, min: 679219618858 -> 3760312702877
}

fun readFormula(name: String): String = readInput(name)[0]
fun readRules(name: String): Map<String, String> = readInput(name)
    .drop(2)
    .map { it.split(" -> ") }
    .map { it[0] to it[1] }
    .toMap()
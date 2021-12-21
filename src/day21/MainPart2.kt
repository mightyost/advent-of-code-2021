package day21p

import java.lang.Long.max

fun calcFrequencies(): Map<Int, Int> {
    val freqs = mutableMapOf<Int, Int>().withDefault { 0 }
    for (i in 1..3) {
        for (j in 1..3) {
            for (k in 1..3) {
                val sum = i+j+k
                freqs[sum] = freqs.getValue(sum) + 1
            }
        }
    }
    return freqs
}

data class Game(
    val aScore: Int,
    val bScore: Int,
    val aPos: Int,
    val bPos: Int,
    val isATurn: Boolean
) {
    fun move(diceSum: Int): Game =
        if (isATurn) {
            val nextPos = (aPos - 1 + diceSum) % 10 + 1
            Game(aScore + nextPos, bScore, nextPos, bPos, !isATurn)
        } else {
            val nextPos = (bPos - 1 + diceSum) % 10 + 1
            Game(aScore, bScore + nextPos, aPos, nextPos, !isATurn)
        }

    fun wins(): Wins? {
        if (aScore >= 21) {
            return Wins(1, 0)
        }
        if (bScore >= 21) {
            return Wins(0, 1)
        }
        return null
    }
}

data class Wins(
    val a: Long,
    val b: Long
)

fun main() {

    val frequencies = calcFrequencies()

    val cache = mutableMapOf<Game, Wins>()

    fun countWins(game: Game): Wins {
        // Cached wins for state?
        cache[game]?.let { return it }

        // Any player won?
        game.wins()?.let {
            return it
                .also { cache[game] = it }
        }

        var totalWins = Wins(0, 0)

        for (diceSum in 3..9) {
            val nextTurn = game.move(diceSum)
            val wins = countWins(nextTurn)
            totalWins = Wins(totalWins.a + wins.a * frequencies[diceSum]!!, totalWins.b + wins.b * frequencies[diceSum]!!)
        }

        cache[game] = totalWins
        return totalWins
    }

    val wins = countWins(Game(0, 0, 2, 10, true))
    val max = max(wins.a, wins.b)

    println(max)

    /*
    49975322685009
    */
}
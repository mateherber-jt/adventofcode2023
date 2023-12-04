package day02

import println
import readInput

private fun getListOfSets(input: String): List<List<Int>> {
    val regex = "Game [0-9]+: (.*)".toRegex()
    val blueRegex = "(?:[0-9]+ [a-z]+,? ?)*([0-9]+) blue,? ?(?:[0-9]+ [a-z]+,? ?)*".toRegex()
    val redRegex = "(?:[0-9]+ [a-z]+,? ?)*([0-9]+) red,? ?(?:[0-9]+ [a-z]+,? ?)*".toRegex()
    val greenRegex = "(?:[0-9]+ [a-z]+,? ?)*([0-9]+) green,? ?(?:[0-9]+ [a-z]+,? ?)*".toRegex()
    return regex.replace(input, "$1").split(";").map { it.trim() }.map { set ->
        listOf(
                redRegex.replace(set, "$1").toIntOrNull() ?: 0,
                greenRegex.replace(set, "$1").toIntOrNull() ?: 0,
                blueRegex.replace(set, "$1").toIntOrNull() ?: 0
        )
    }
}

fun part1(input: List<String>): Int {
    val gameRegex = "Game ([0-9]+): .*".toRegex()
    return input.sumOf { game ->
        val gameNumber = gameRegex.replace(game, "$1").toInt()
        val sets = getListOfSets(game)
        if (sets.all { it[0] <= 12 && it[1] <= 13 && it[2] <= 14 }) {
            gameNumber
        } else {
            0
        }
    }
}

fun part2(input: List<String>): Int {
    return input.sumOf { game ->
        val sets = getListOfSets(game)
        val maxR = sets.maxBy { it[0] }[0]
        val maxG = sets.maxBy { it[1] }[1]
        val maxB = sets.maxBy { it[2] }[2]
        maxR * maxG * maxB
    }
}

fun main() {
    val testInput = readInput("\\day02\\Day02_test")
    val testInput2 = readInput("\\day02\\Day02_test2")
    check(part1(testInput) == 8)
    check(part2(testInput2) == 2286)

    val input = readInput("\\day02\\Day02")
    part1(input).println()
    part2(input).println()
}

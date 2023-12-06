package day04

import println
import readInput
import kotlin.math.pow

private val lineRegex = "Card *[0-9]+: *((?:[0-9]+ *)*[0-9]+) [|] *((?:[0-9]+ *)*[0-9]+)".toRegex()

// TODO never generalize & cleanup
fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val winning =
            line
                .replace(lineRegex, "$1")
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
        val numbers =
            line
                .replace(lineRegex, "$2")
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
        val matching = winning.intersect(numbers)
        (2.0).pow(matching.size - 1).toInt()
    }
}

fun part2(input: List<String>): Int {
    val cards = IntArray(input.size) { 1 }
    input.forEachIndexed { index, line ->
        val winning =
            line
                .replace(lineRegex, "$1")
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
        val numbers =
            line
                .replace(lineRegex, "$2")
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
        val matching = winning.intersect(numbers)
        for (i in 1..kotlin.math.min(matching.size, input.size - index - 1)) {
            cards[index + i] += cards[index]
        }
    }
    return cards.sum()
}

fun main() {
    val testInput = readInput("\\day04\\Day04_test")
        val testInput2 = readInput("\\day04\\Day04_test2")
    check(part1(testInput) == 13)
        check(part2(testInput2) == 30)

    val input = readInput("\\day04\\Day04")
    part1(input).println()
    part2(input).println()
}

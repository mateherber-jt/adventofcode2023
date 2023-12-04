package day01

import indexOfOrNull
import lastIndexOfOrNull
import println
import readInput

data class Number(val numberForm: String, val wordForm: String, val value: Int)

val numbers = listOf(
        Number("1", "one", 1),
        Number("2", "two", 2),
        Number("3", "three", 3),
        Number("4", "four", 4),
        Number("5", "five", 5),
        Number("6", "six", 6),
        Number("7", "seven", 7),
        Number("8", "eight", 8),
        Number("9", "nine", 9),
)

private fun String.getValue(useWordForm: Boolean): Int {
    val first = numbers.mapNotNull { number ->
        if (useWordForm) {
            listOfNotNull(indexOfOrNull(number.numberForm), indexOfOrNull(number.wordForm)).minOrNull()?.let { number.value to it }
        } else {
            indexOfOrNull(number.numberForm)?.let { number.value to it }
        }
    }.minByOrNull { it.second }!!
    val last = numbers.mapNotNull { number ->
        if (useWordForm) {
            listOfNotNull(lastIndexOfOrNull(number.numberForm), lastIndexOfOrNull(number.wordForm)).maxOrNull()?.let { number.value to it }
        } else {
            lastIndexOfOrNull(number.numberForm)?.let { number.value to it }
        }
    }.maxByOrNull { it.second }!!
    return first.first * 10 + last.first
}


fun part1(input: List<String>): Int {
    return input.sumOf { it.getValue(false) }
}

fun part2(input: List<String>): Int {
    return input.sumOf { it.getValue(true) }
}

fun main() {
    val testInput = readInput("\\day01\\Day01_test")
    val testInput2 = readInput("\\day01\\Day01_test2")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("\\day01\\Day01")
    part1(input).println()
    part2(input).println()
}




package day06

import println
import readInput
import toLongList

val timeRegex = "Time:((?: *[0-9]+)*)".toRegex()
val distanceRegex = "Distance:((?: *[0-9]+)*)".toRegex()

private fun bestTime(time: Long, distance: Long): Long {
    var ok = false
    var first = -1L
    var last = -1L
    for (i in (0..time)) {
        if ((time - i) * i > distance) {
            if (!ok) {
                first = i
            }
            ok = true
        } else {
            if (ok) {
                last = i - 1
            }
            ok = false
        }
    }
    return last - first + 1
}

fun part1(input: List<String>): Long {
    val times = timeRegex.replace(input[0], "$1").toLongList()
    val distances = distanceRegex.replace(input[1], "$1").toLongList()
    val rounds = times.zip(distances)
    return rounds.fold(1L) { acc, (time, distance) -> acc * bestTime(time, distance) }
}

fun part2(input: List<String>): Long {
    val time = timeRegex.replace(input[0], "$1").replace(" ", "").toLong()
    val distance = distanceRegex.replace(input[1], "$1").replace(" ", "").toLong()
    return bestTime(time, distance)
}

fun main() {
    val testInput = readInput("\\day06\\Day06_test")
    val testInput2 = readInput("\\day06\\Day06_test2")
    check(part1(testInput) == 288L)
    check(part2(testInput2) == 71503L)

    val input = readInput("\\day06\\Day06")
    part1(input).println()
    part2(input).println()
}

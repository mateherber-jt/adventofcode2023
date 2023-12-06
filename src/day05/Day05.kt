package day05

import println
import readInput
import toLongList

val itemRegex = "[^:]*: (.*)".toRegex()

fun parseInput(
    input: List<String>,
): Pair<List<Long>, List<List<Mapping>>> {
    val items = itemRegex.replace(input.first(), "$1").toLongList()
    val mappingInput = input.drop(2)

    val mappings = mutableListOf<List<Mapping>>()
    var currentMappings = mutableListOf<Mapping>()
    mappingInput.forEach { line ->
        when {
            line.isBlank() -> {
                mappings += currentMappings
                currentMappings = mutableListOf()
            }
            line.endsWith(":") -> {}
            else -> {
                val rawMapping = line.toLongList()
                currentMappings +=
                    Mapping(
                        rawMapping[0] - rawMapping[1],
                        rawMapping[1] ..< rawMapping[1] + rawMapping[2]
                    )
            }
        }
    }
    mappings += currentMappings
    return items to mappings
}

fun part1(input: List<String>): Long {
    val (items, mapping) = parseInput(input)
    return mapping
        .fold(items) { acc, m ->
            acc.map { item ->
                val matchingRange = m.firstOrNull { it.range.contains(item) }
                if (matchingRange == null) {
                    item
                } else {
                    item + matchingRange.diff
                }
            }
        }
        .min()
}

fun part2(input: List<String>): Long {
    var min = Long.MAX_VALUE
    val (items, mapping) = parseInput(input)
    val list = items.windowed(2, 2).map { it[0] ..< it[0] + it[1] }
    list.forEach { range ->
        for (i in range) {
            val newMin = mapping.fold(i) { acc, m ->
                val matchingRange = m.firstOrNull { it.range.contains(acc) }
                if (matchingRange == null) {
                    acc
                } else {
                    acc + matchingRange.diff
                }
            }
            min = kotlin.math.min(min, newMin)
        }
    }
    return min
}

fun main() {
    val testInput = readInput("\\day05\\Day05_test")
    val testInput2 = readInput("\\day05\\Day05_test2")
    check(part1(testInput) == 35L)
    check(part2(testInput2) == 46L)

    val input = readInput("\\day05\\Day05")
    part1(input).println()
    part2(input).println()
}

data class Mapping(val diff: Long, val range: LongRange)

package com.zhdanon.fitnessapp.domain.util

import com.zhdanon.fitnessapp.domain.models.workouts.Reps

fun String.toReps(): Reps {
    val raw = this.trim().removeSurrounding("\"").trim()

    if (raw.isBlank()) return Reps.Fixed(0)

    if (raw.equals("max", ignoreCase = true)) {
        return Reps.Range(Int.MAX_VALUE, Int.MAX_VALUE)
    }

    if (raw.contains("сек", ignoreCase = true)) {
        val cleaned = raw.replace("сек", "").trim()
        return if ("-" in cleaned) {
            val (from, to) = cleaned.split("-").map { it.trim().toInt() }
            Reps.TimeRange(from, to)
        } else {
            Reps.TimeFixed(cleaned.toInt())
        }
    }

    if (raw.contains("s-")) {
        val (from, to) = raw.split("-")
        val fromVal = from.removeSuffix("s").trim().toInt()
        val toVal = to.replace("сек", "").removeSuffix("s").trim().toInt()
        return Reps.TimeRange(fromVal, toVal)
    }

    if (raw.endsWith("s")) {
        return Reps.TimeFixed(raw.removeSuffix("s").trim().toInt())
    }

    if ("-" in raw) {
        val (from, to) = raw.split("-").map { it.trim().toInt() }
        return Reps.Range(from, to)
    }

    return raw.toIntOrNull()?.let { Reps.Fixed(it) } ?: Reps.Fixed(0)
}
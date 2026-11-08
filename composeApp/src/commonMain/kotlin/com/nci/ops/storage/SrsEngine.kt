package com.nci.ops.storage

import com.nci.ops.model.CurriculumTrackId
import com.nci.ops.model.SrsFlashcard
import kotlin.math.max
import kotlin.math.roundToInt

enum class RecallGrade(val label: String, val quality: Int, val subtitle: String) {
    AGAIN("Again", 1, "< 10m"),
    HARD("Hard", 3, "1d"),
    GOOD("Good", 4, "3d+"),
    EASY("Easy", 5, "7d+")
}

object SrsEngine {

    /**
     * Standard SM-2 calculation updating intervalDays, repetitions, easeFactor,
     * and real epoch timestamp for the next review.
     */
    fun reviewCard(card: SrsFlashcard, grade: RecallGrade, currentEpochMs: Long, maxDueOrder: Int): SrsFlashcard {
        val q = grade.quality
        val newRepetitions: Int
        val newInterval: Int

        if (q < 3) {
            newRepetitions = 0
            newInterval = 1
        } else {
            newRepetitions = card.repetitions + 1
            newInterval = when (newRepetitions) {
                1 -> if (grade == RecallGrade.EASY) 4 else 1
                2 -> 6
                else -> (card.intervalDays * card.easeFactor).roundToInt().coerceAtLeast(1)
            }
        }

        val efDelta = 0.1 - (5 - q) * (0.08 + (5 - q) * 0.02)
        val newEaseFactor = max(1.3, card.easeFactor + efDelta)
        val nextReviewMs = currentEpochMs + (newInterval.toLong() * 86_400_000L)

        return card.copy(
            intervalDays = newInterval,
            repetitions = newRepetitions,
            easeFactor = newEaseFactor,
            dueOrder = maxDueOrder + newInterval,
            lastReviewedEpochMs = currentEpochMs,
            nextReviewEpochMs = nextReviewMs
        )
    }

    /**
     * Parses raw pasted book/course notes into structured SM-2 Flashcards.
     * Flagged as `isVerified = true` because they are imported directly from the user's materials.
     */
    fun parseNotesIntoFlashcards(
        rawText: String,
        track: CurriculumTrackId,
        existingCount: Int,
        currentEpochMs: Long
    ): List<SrsFlashcard> {
        val lines = rawText.lines()
            .map { it.trim().removePrefix("-").removePrefix("*").removePrefix("•").trim() }
            .filter { it.length >= 10 }

        val generated = mutableListOf<SrsFlashcard>()
        lines.forEachIndexed { idx, line ->
            val colonIdx = line.indexOfAny(charArrayOf(':', '—', '-'))
            if (colonIdx in 3..(line.length - 6)) {
                val concept = line.substring(0, colonIdx).trim()
                val explanation = line.substring(colonIdx + 1).trim()
                generated.add(
                    SrsFlashcard(
                        id = "custom_fc_${existingCount + idx + 1}_${concept.hashCode()}",
                        trackId = track,
                        frontPrompt = "Define & apply [$concept] from ${track.shortBadge}:",
                        backAnswer = explanation,
                        tacticalTip = "Book-Verified Note • Drill this cue in your next Tier 1–5 Field Op.",
                        dueOrder = idx + 1,
                        nextReviewEpochMs = currentEpochMs, // immediately due
                        isCustom = true,
                        isVerified = true
                    )
                )
            } else {
                val words = line.split(" ")
                val preview = words.take(6).joinToString(" ")
                generated.add(
                    SrsFlashcard(
                        id = "custom_fc_${existingCount + idx + 1}_${preview.hashCode()}",
                        trackId = track,
                        frontPrompt = "Key Principle (${track.shortBadge}): \"$preview...\" — What is the core behavioral rule?",
                        backAnswer = line,
                        tacticalTip = "Book-Verified Note • Integrate this principle into your pre-interaction briefing.",
                        dueOrder = idx + 1,
                        nextReviewEpochMs = currentEpochMs, // immediately due
                        isCustom = true,
                        isVerified = true
                    )
                )
            }
        }
        return generated
    }
}

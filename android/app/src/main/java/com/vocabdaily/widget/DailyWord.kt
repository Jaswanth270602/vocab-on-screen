package com.vocabdaily.widget

import android.content.Context
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class TodayCard(
    val word: VocabWord,
    val dayLabel: String,
    val dayNumber: String,
)

object DailyWord {
    private const val PREFS = "vocab_daily_prefs"
    private const val KEY_START_EPOCH_DAY = "start_epoch_day"

    /**
     * Uses the phone's local timezone (not UTC, not hardcoded dates).
     * Day 0 = install / first open → word 1.
     * Then one new word each local midnight, wrapping when the list ends.
     */
    fun dayIndex(context: Context, nowMillis: Long = System.currentTimeMillis()): Int {
        val n = VocabData.words.size
        if (n == 0) return 0
        val start = startLocalEpochDay(context, nowMillis)
        val today = localEpochDay(nowMillis)
        val daysSinceStart = (today - start).toInt().coerceAtLeast(0)
        return daysSinceStart % n
    }

    fun today(context: Context, nowMillis: Long = System.currentTimeMillis()): TodayCard {
        val zone = ZoneId.systemDefault()
        val index = dayIndex(context, nowMillis)
        val word = VocabData.words[index]
        val dayLabel = DateTimeFormatter.ofPattern("EEEE, MMM d", Locale.getDefault())
            .withZone(zone)
            .format(Instant.ofEpochMilli(nowMillis))
        return TodayCard(
            word = word,
            dayLabel = dayLabel,
            dayNumber = "${index + 1} / ${VocabData.words.size}",
        )
    }

    private fun startLocalEpochDay(context: Context, nowMillis: Long): Long {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        if (!prefs.contains(KEY_START_EPOCH_DAY)) {
            val start = localEpochDay(nowMillis)
            prefs.edit().putLong(KEY_START_EPOCH_DAY, start).apply()
            return start
        }
        return prefs.getLong(KEY_START_EPOCH_DAY, localEpochDay(nowMillis))
    }

    private fun localEpochDay(nowMillis: Long): Long {
        return Instant.ofEpochMilli(nowMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .toEpochDay()
    }
}

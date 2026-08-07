package com.vocabdaily.widget

import android.content.Context
import java.time.Instant
import java.time.ZoneOffset
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

    private val dayLabelFmt =
        DateTimeFormatter.ofPattern("EEEE, MMM d", Locale.US).withZone(ZoneOffset.UTC)

    /**
     * Day 0 = install / first open → word 1.
     * Then one new word per UTC day, wrapping to the start after the list ends.
     */
    fun dayIndex(context: Context, nowMillis: Long = System.currentTimeMillis()): Int {
        val n = VocabData.words.size
        if (n == 0) return 0
        val start = startEpochDay(context, nowMillis)
        val today = utcEpochDay(nowMillis)
        val daysSinceStart = (today - start).toInt().coerceAtLeast(0)
        return daysSinceStart % n
    }

    fun today(context: Context, nowMillis: Long = System.currentTimeMillis()): TodayCard {
        val index = dayIndex(context, nowMillis)
        val word = VocabData.words[index]
        return TodayCard(
            word = word,
            dayLabel = dayLabelFmt.format(Instant.ofEpochMilli(nowMillis)),
            dayNumber = "${index + 1} / ${VocabData.words.size}",
        )
    }

    private fun startEpochDay(context: Context, nowMillis: Long): Long {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        if (!prefs.contains(KEY_START_EPOCH_DAY)) {
            val start = utcEpochDay(nowMillis)
            prefs.edit().putLong(KEY_START_EPOCH_DAY, start).apply()
            return start
        }
        return prefs.getLong(KEY_START_EPOCH_DAY, utcEpochDay(nowMillis))
    }

    private fun utcEpochDay(nowMillis: Long): Long {
        val date = Instant.ofEpochMilli(nowMillis).atZone(ZoneOffset.UTC).toLocalDate()
        return date.toEpochDay()
    }
}

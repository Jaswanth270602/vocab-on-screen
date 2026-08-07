package com.vocabdaily.widget

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

data class TodayCard(
    val word: VocabWord,
    val dayLabel: String,
    val dayNumber: String,
)

object DailyWord {
    /** Same epoch as the web app: UTC midnight 2026-01-01 */
    private val epochUtcMillis = Instant.parse("2026-01-01T00:00:00Z").toEpochMilli()
    private val dayLabelFmt =
        DateTimeFormatter.ofPattern("EEEE, MMM d", Locale.US).withZone(ZoneOffset.UTC)

    fun utcDayIndex(nowMillis: Long = System.currentTimeMillis()): Int {
        val todayUtc = Instant.ofEpochMilli(nowMillis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        val days = TimeUnit.MILLISECONDS.toDays(todayUtc - epochUtcMillis).toInt()
        val n = VocabData.words.size
        return ((days % n) + n) % n
    }

    fun today(nowMillis: Long = System.currentTimeMillis()): TodayCard {
        val index = utcDayIndex(nowMillis)
        val word = VocabData.words[index]
        return TodayCard(
            word = word,
            dayLabel = dayLabelFmt.format(Instant.ofEpochMilli(nowMillis)),
            dayNumber = "${index + 1} / ${VocabData.words.size}",
        )
    }
}

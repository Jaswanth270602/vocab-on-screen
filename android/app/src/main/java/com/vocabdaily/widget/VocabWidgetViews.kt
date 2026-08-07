package com.vocabdaily.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.RemoteViews

object VocabWidgetViews {
    private const val TAG = "VocabWidgetViews"

    fun content(
        context: Context,
        options: Bundle? = null,
    ): RemoteViews {
        return try {
            val theme = ThemePrefs.get(context)
            val today = DailyWord.today(context)
            val word = today.word
            val ink = ThemePrefs.color(context, theme.ink)
            val inkSoft = ThemePrefs.color(context, theme.inkSoft)
            val accent = ThemePrefs.color(context, theme.accent)
            val muted = blend(inkSoft, Color.WHITE, 0.25f)
            val caption = blend(inkSoft, Color.WHITE, 0.45f)

            val minWidth = options?.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 0) ?: 0
            val minHeight = options?.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 0) ?: 0
            // Short cells → compact layout. Default / medium → full card (content-sized).
            val layout =
                if (minHeight in 1 until 135) R.layout.widget_vocab_compact
                else R.layout.widget_vocab

            val countChip = when (theme) {
                AppTheme.GROVE -> R.drawable.bg_count_chip_grove
                AppTheme.INK -> R.drawable.bg_count_chip_ink
                AppTheme.COAST -> R.drawable.bg_count_chip
            }

            RemoteViews(context.packageName, layout).apply {
                setInt(R.id.widget_root, "setBackgroundResource", theme.widgetBackground)
                setInt(R.id.widget_count, "setBackgroundResource", countChip)
                setInt(R.id.widget_root_bar, "setBackgroundColor", accent)

                setTextViewText(R.id.widget_day, today.dayLabel)
                setTextViewText(R.id.widget_count, today.dayNumber)
                setTextViewText(R.id.widget_eyebrow, context.getString(R.string.widget_eyebrow))
                setTextViewText(R.id.widget_word, word.word)
                setTextViewText(R.id.widget_meaning, word.meaning)
                setTextViewText(R.id.widget_root_caption, context.getString(R.string.widget_root_caption))
                setTextViewText(R.id.widget_etymology, "${word.root} · ${word.rootMeaning}")
                setTextViewText(R.id.widget_example, "“${word.example}”")

                setTextColor(R.id.widget_day, muted)
                setTextColor(R.id.widget_count, accent)
                setTextColor(R.id.widget_eyebrow, accent)
                setTextColor(R.id.widget_word, ink)
                setTextColor(R.id.widget_meaning, inkSoft)
                setTextColor(R.id.widget_root_caption, caption)
                setTextColor(R.id.widget_etymology, accent)
                setTextColor(R.id.widget_example, muted)

                applyResponsiveSizing(
                    context,
                    minWidth,
                    minHeight,
                    layout == R.layout.widget_vocab_compact,
                )

                setOnClickPendingIntent(R.id.widget_root, openAppPendingIntent(context))
            }
        } catch (t: Throwable) {
            Log.e(TAG, "Failed to build widget views", t)
            fallback(context)
        }
    }

    /**
     * Adapt type + padding to the actual launcher cell.
     * Never stretch empty regions — keep content packed; if the user
     * makes the cell taller, center vertically so gaps aren't all at the bottom.
     */
    private fun RemoteViews.applyResponsiveSizing(
        context: Context,
        minWidthDp: Int,
        minHeightDp: Int,
        compact: Boolean,
    ) {
        val density = context.resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density).toInt()

        val hPad = when {
            minWidthDp >= 360 -> 18
            minWidthDp >= 300 -> 16
            minWidthDp >= 240 -> 14
            minWidthDp > 0 -> 12
            else -> 16
        }
        val vPad = when {
            compact -> 8
            minHeightDp in 1 until 160 -> 9
            else -> 10
        }
        setViewPadding(R.id.widget_root, dp(hPad), dp(vPad), dp(hPad), dp(vPad))

        // Pack to top for normal size; center only if user stretched the cell taller.
        val gravity =
            if (!compact && minHeightDp >= 190) Gravity.CENTER_VERTICAL
            else Gravity.TOP
        setInt(R.id.widget_root, "setGravity", gravity)

        if (compact) {
            setTextViewTextSize(R.id.widget_word, TypedValue.COMPLEX_UNIT_SP, 20f)
            setTextViewTextSize(R.id.widget_meaning, TypedValue.COMPLEX_UNIT_SP, 12f)
            setTextViewTextSize(R.id.widget_example, TypedValue.COMPLEX_UNIT_SP, 11f)
            setInt(R.id.widget_meaning, "setMaxLines", 2)
            setInt(R.id.widget_example, "setMaxLines", 1)
            return
        }

        val wordSp = when {
            minWidthDp >= 340 -> 28f
            minWidthDp >= 280 -> 26f
            else -> 24f
        }
        val meaningSp = if (minWidthDp >= 300) 13.5f else 13f
        val exampleSp = if (minWidthDp >= 300) 12.5f else 12f

        // Prefer 2 lines so height stays close to content; allow 3 only if tall.
        val meaningLines = if (minHeightDp >= 190) 3 else 2
        val exampleLines = if (minHeightDp >= 190) 3 else 2

        setTextViewTextSize(R.id.widget_word, TypedValue.COMPLEX_UNIT_SP, wordSp)
        setTextViewTextSize(R.id.widget_meaning, TypedValue.COMPLEX_UNIT_SP, meaningSp)
        setTextViewTextSize(R.id.widget_example, TypedValue.COMPLEX_UNIT_SP, exampleSp)
        setTextViewTextSize(
            R.id.widget_etymology,
            TypedValue.COMPLEX_UNIT_SP,
            if (minWidthDp >= 300) 12.5f else 12f,
        )

        setInt(R.id.widget_meaning, "setMaxLines", meaningLines)
        setInt(R.id.widget_example, "setMaxLines", exampleLines)
        setInt(R.id.widget_etymology, "setMaxLines", 1)
        setInt(R.id.widget_word, "setMaxLines", 1)
    }

    fun push(context: Context, appWidgetIds: IntArray) {
        val manager = AppWidgetManager.getInstance(context)
        for (id in appWidgetIds) {
            try {
                val options = manager.getAppWidgetOptions(id)
                manager.updateAppWidget(id, content(context, options))
            } catch (t: Throwable) {
                Log.e(TAG, "Failed to update widget $id", t)
                manager.updateAppWidget(id, fallback(context))
            }
        }
    }

    fun pushAll(context: Context) {
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(ComponentName(context, VocabWidgetProvider::class.java))
        if (ids.isNotEmpty()) {
            push(context, ids)
        }
    }

    private fun blend(color: Int, onto: Int, ontoAmount: Float): Int {
        val t = ontoAmount.coerceIn(0f, 1f)
        val r = (Color.red(color) * (1 - t) + Color.red(onto) * t).toInt()
        val g = (Color.green(color) * (1 - t) + Color.green(onto) * t).toInt()
        val b = (Color.blue(color) * (1 - t) + Color.blue(onto) * t).toInt()
        return Color.rgb(r, g, b)
    }

    private fun fallback(context: Context): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_fallback).apply {
            setTextViewText(R.id.fallback_text, "Vocab Daily\nOpen app · Refresh widget")
            setOnClickPendingIntent(R.id.fallback_root, openAppPendingIntent(context))
        }
    }

    private fun openAppPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }
}

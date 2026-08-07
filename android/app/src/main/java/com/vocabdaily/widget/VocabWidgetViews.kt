package com.vocabdaily.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews

object VocabWidgetViews {
    private const val TAG = "VocabWidgetViews"

    fun content(
        context: Context,
        @Suppress("UNUSED_PARAMETER") options: Bundle? = null,
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

            val countChip = when (theme) {
                AppTheme.GROVE -> R.drawable.bg_count_chip_grove
                AppTheme.INK -> R.drawable.bg_count_chip_ink
                AppTheme.COAST -> R.drawable.bg_count_chip
            }

            RemoteViews(context.packageName, R.layout.widget_vocab).apply {
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

                // Distinct roles: meta / label / hero / body / accent / quote
                setTextColor(R.id.widget_day, muted)
                setTextColor(R.id.widget_count, accent)
                setTextColor(R.id.widget_eyebrow, accent)
                setTextColor(R.id.widget_word, ink)
                setTextColor(R.id.widget_meaning, inkSoft)
                setTextColor(R.id.widget_root_caption, caption)
                setTextColor(R.id.widget_etymology, accent)
                setTextColor(R.id.widget_example, muted)

                setOnClickPendingIntent(R.id.widget_root, openAppPendingIntent(context))
            }
        } catch (t: Throwable) {
            Log.e(TAG, "Failed to build widget views", t)
            fallback(context)
        }
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

package com.vocabdaily.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            val minHeight = options?.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 0) ?: 0
            val layout = theme.layoutForHeightDp(minHeight)
            val word = today.word

            RemoteViews(context.packageName, layout).apply {
                setTextViewText(R.id.widget_day, today.dayLabel)
                setTextViewText(R.id.widget_count, today.dayNumber)
                setTextViewText(R.id.widget_word, word.word)
                setTextViewText(R.id.widget_meaning, word.meaning)
                setTextViewText(R.id.widget_etymology, "${word.root} · ${word.rootMeaning}")
                setTextViewText(R.id.widget_example, "“${word.example}”")
                setOnClickPendingIntent(R.id.widget_root, openAppPendingIntent(context))
            }
        } catch (t: Throwable) {
            Log.e(TAG, "Failed to build widget views", t)
            // Ultra-simple fallback so the host never shows "Can't load widget"
            RemoteViews(context.packageName, R.layout.widget_fallback).apply {
                setTextViewText(R.id.fallback_text, "Vocab Daily\nTap to open")
                setOnClickPendingIntent(R.id.fallback_root, openAppPendingIntent(context))
            }
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
                manager.updateAppWidget(
                    id,
                    RemoteViews(context.packageName, R.layout.widget_fallback).apply {
                        setTextViewText(R.id.fallback_text, "Vocab Daily\nTap to open")
                        setOnClickPendingIntent(R.id.fallback_root, openAppPendingIntent(context))
                    },
                )
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

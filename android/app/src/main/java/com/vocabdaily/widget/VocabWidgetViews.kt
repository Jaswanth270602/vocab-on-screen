package com.vocabdaily.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

object VocabWidgetViews {
    fun content(context: Context, today: TodayCard = DailyWord.today()): RemoteViews {
        val word = today.word
        return RemoteViews(context.packageName, R.layout.widget_vocab).apply {
            setTextViewText(R.id.widget_day, today.dayLabel)
            setTextViewText(R.id.widget_count, today.dayNumber)
            setTextViewText(R.id.widget_word, word.word)
            setTextViewText(R.id.widget_meaning, word.meaning)
            setTextViewText(R.id.widget_root, "${word.root} · ${word.rootMeaning}")
            setTextViewText(R.id.widget_example, "“${word.example}”")
            setOnClickPendingIntent(R.id.widget_root, openAppPendingIntent(context))
        }
    }

    fun push(context: Context, appWidgetIds: IntArray) {
        val manager = AppWidgetManager.getInstance(context)
        val views = content(context)
        for (id in appWidgetIds) {
            manager.updateAppWidget(id, views)
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

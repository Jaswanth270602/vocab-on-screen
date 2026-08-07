package com.vocabdaily.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.util.Log

class VocabWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        try {
            VocabWidgetViews.push(context, appWidgetIds)
        } catch (t: Throwable) {
            Log.e("VocabWidgetProvider", "onUpdate failed", t)
        }
    }

    override fun onEnabled(context: Context) {
        try {
            VocabWidgetViews.pushAll(context)
        } catch (t: Throwable) {
            Log.e("VocabWidgetProvider", "onEnabled failed", t)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle,
    ) {
        try {
            appWidgetManager.updateAppWidget(
                appWidgetId,
                VocabWidgetViews.content(context, newOptions),
            )
        } catch (t: Throwable) {
            Log.e("VocabWidgetProvider", "onAppWidgetOptionsChanged failed", t)
        }
    }
}

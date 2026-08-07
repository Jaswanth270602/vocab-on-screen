package com.vocabdaily.widget

import android.content.Context

enum class AppTheme(
    val id: String,
    val title: String,
    val subtitle: String,
    val widgetLayout: Int,
    val widgetLayoutCompact: Int,
    val cardBackground: Int,
    val pageBg: Int,
    val titleColor: Int,
    val taglineColor: Int,
    val bodyColor: Int,
    val ink: Int,
    val inkSoft: Int,
    val accent: Int,
    val wordFont: Int,
    val bodyFont: Int,
) {
    GROVE(
        id = "grove",
        title = "Grove",
        subtitle = "Soft glass green · clean sans",
        widgetLayout = R.layout.widget_theme_grove,
        widgetLayoutCompact = R.layout.widget_theme_grove_compact,
        cardBackground = R.drawable.bg_card_grove,
        pageBg = R.color.grove_page,
        titleColor = R.color.grove_fog,
        taglineColor = R.color.grove_accent_soft,
        bodyColor = R.color.grove_fog,
        ink = R.color.grove_ink,
        inkSoft = R.color.grove_ink_soft,
        accent = R.color.grove_accent,
        wordFont = R.font.dm_sans_semibold,
        bodyFont = R.font.dm_sans_regular,
    ),
    INK(
        id = "ink",
        title = "Ink",
        subtitle = "Glass paper · literary serif",
        widgetLayout = R.layout.widget_theme_ink,
        widgetLayoutCompact = R.layout.widget_theme_ink_compact,
        cardBackground = R.drawable.bg_card_ink,
        pageBg = R.color.ink_page,
        titleColor = R.color.ink_fog,
        taglineColor = R.color.ink_accent_soft,
        bodyColor = R.color.ink_fog,
        ink = R.color.ink_ink,
        inkSoft = R.color.ink_ink_soft,
        accent = R.color.ink_accent,
        wordFont = R.font.literata_bold,
        bodyFont = R.font.literata_regular,
    ),
    COAST(
        id = "coast",
        title = "Coast",
        subtitle = "Sky glass · wallpaper shows through",
        widgetLayout = R.layout.widget_theme_coast,
        widgetLayoutCompact = R.layout.widget_theme_coast_compact,
        cardBackground = R.drawable.bg_card_coast,
        pageBg = R.color.coast_page,
        titleColor = R.color.coast_fog,
        taglineColor = R.color.coast_accent_soft,
        bodyColor = R.color.coast_fog,
        ink = R.color.coast_ink,
        inkSoft = R.color.coast_ink_soft,
        accent = R.color.coast_accent,
        wordFont = R.font.literata_bold,
        bodyFont = R.font.dm_sans_regular,
    );

    fun layoutForHeightDp(minHeightDp: Int): Int =
        if (minHeightDp > 0 && minHeightDp < COMPACT_MAX_HEIGHT_DP) {
            widgetLayoutCompact
        } else {
            widgetLayout
        }

    companion object {
        /** Below this height (dp), use the compact card layout. */
        const val COMPACT_MAX_HEIGHT_DP = 140

        fun fromId(id: String?): AppTheme =
            entries.firstOrNull { it.id == id } ?: COAST
    }
}

object ThemePrefs {
    private const val PREFS = "vocab_daily_prefs"
    private const val KEY_THEME = "theme_id"

    fun get(context: Context): AppTheme {
        val id = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_THEME, AppTheme.COAST.id)
        return AppTheme.fromId(id)
    }

    fun set(context: Context, theme: AppTheme) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_THEME, theme.id)
            .apply()
    }
}

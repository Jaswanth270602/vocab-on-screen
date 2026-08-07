package com.vocabdaily.widget

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<LinearLayout>(R.id.theme_grove).setOnClickListener { selectTheme(AppTheme.GROVE) }
        findViewById<LinearLayout>(R.id.theme_ink).setOnClickListener { selectTheme(AppTheme.INK) }
        findViewById<LinearLayout>(R.id.theme_coast).setOnClickListener { selectTheme(AppTheme.COAST) }

        findViewById<Button>(R.id.main_refresh_widget).setOnClickListener {
            VocabWidgetViews.pushAll(this)
            applyTheme(ThemePrefs.get(this))
        }

        applyTheme(ThemePrefs.get(this))
    }

    override fun onResume() {
        super.onResume()
        applyTheme(ThemePrefs.get(this))
        VocabWidgetViews.pushAll(this)
    }

    private fun selectTheme(theme: AppTheme) {
        ThemePrefs.set(this, theme)
        applyTheme(theme)
        VocabWidgetViews.pushAll(this)
    }

    private fun applyTheme(theme: AppTheme) {
        val pageBg = ContextCompat.getColor(this, theme.pageBg)
        val titleColor = ContextCompat.getColor(this, theme.titleColor)
        val taglineColor = ContextCompat.getColor(this, theme.taglineColor)
        val bodyColor = ContextCompat.getColor(this, theme.bodyColor)
        val ink = ContextCompat.getColor(this, theme.ink)
        val inkSoft = ContextCompat.getColor(this, theme.inkSoft)
        val accent = ContextCompat.getColor(this, theme.accent)

        val wordTypeface = ResourcesCompat.getFont(this, theme.wordFont) ?: Typeface.DEFAULT_BOLD
        val bodyTypeface = ResourcesCompat.getFont(this, theme.bodyFont) ?: Typeface.DEFAULT

        findViewById<ScrollView>(R.id.main_scroll).setBackgroundColor(pageBg)

        findViewById<TextView>(R.id.main_title).apply {
            setTextColor(titleColor)
            typeface = wordTypeface
        }
        findViewById<TextView>(R.id.main_tagline).apply {
            setTextColor(taglineColor)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.theme_label).apply {
            setTextColor(taglineColor)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.main_instructions).apply {
            setTextColor(bodyColor)
            typeface = bodyTypeface
        }

        val chipNormal =
            if (theme == AppTheme.COAST) R.drawable.bg_theme_chip_dark else R.drawable.bg_theme_chip
        val chipSelected =
            if (theme == AppTheme.COAST) R.drawable.bg_theme_chip_dark_selected else R.drawable.bg_theme_chip_selected

        styleThemeChip(R.id.theme_grove, R.id.theme_grove_title, R.id.theme_grove_sub, theme == AppTheme.GROVE, titleColor, taglineColor, bodyTypeface, chipNormal, chipSelected)
        styleThemeChip(R.id.theme_ink, R.id.theme_ink_title, R.id.theme_ink_sub, theme == AppTheme.INK, titleColor, taglineColor, bodyTypeface, chipNormal, chipSelected)
        styleThemeChip(R.id.theme_coast, R.id.theme_coast_title, R.id.theme_coast_sub, theme == AppTheme.COAST, titleColor, taglineColor, bodyTypeface, chipNormal, chipSelected)

        val card = findViewById<LinearLayout>(R.id.preview_card)
        card.setBackgroundResource(theme.cardBackground)

        val today = DailyWord.today()
        val word = today.word

        findViewById<TextView>(R.id.card_day).apply {
            text = today.dayLabel
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.card_count).apply {
            text = today.dayNumber
            setTextColor(accent)
            typeface = wordTypeface
        }
        findViewById<TextView>(R.id.card_eyebrow).apply {
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.card_word).apply {
            text = word.word
            setTextColor(ink)
            typeface = wordTypeface
        }
        findViewById<TextView>(R.id.card_meaning).apply {
            text = word.meaning
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.card_root).apply {
            text = "${word.root} · ${word.rootMeaning}"
            setTextColor(accent)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.card_example).apply {
            text = "“${word.example}”"
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
    }

    private fun styleThemeChip(
        chipId: Int,
        titleId: Int,
        subId: Int,
        selected: Boolean,
        titleColor: Int,
        subColor: Int,
        typeface: Typeface,
        chipNormal: Int,
        chipSelected: Int,
    ) {
        findViewById<LinearLayout>(chipId).setBackgroundResource(
            if (selected) chipSelected else chipNormal,
        )
        findViewById<TextView>(titleId).apply {
            setTextColor(titleColor)
            this.typeface = typeface
        }
        findViewById<TextView>(subId).apply {
            setTextColor(subColor)
            this.typeface = typeface
        }
    }
}

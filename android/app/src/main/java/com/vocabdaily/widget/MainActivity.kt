package com.vocabdaily.widget

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils

class MainActivity : AppCompatActivity() {
    private val vocabDeck = RandomDeck()
    private val bankDeck = PracticeDeck()
    private var currentVocab: VocabWord? = null
    private var currentBank: PracticeItem? = null

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

        findViewById<Button>(R.id.btn_contact_developer).setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/jaswanth-kumar-palavalasa-570549248/"),
                ),
            )
        }

        val vocabSwipe = findViewById<SwipeCardView>(R.id.swipe_card)
        vocabSwipe.preferredDirection = SwipeDirection.LEFT
        vocabSwipe.onSwiped = { showNextVocab(animateIn = true) }
        findViewById<ImageButton>(R.id.btn_swipe_left).setOnClickListener {
            vocabSwipe.animateSwipeLeft()
        }
        findViewById<LinearLayout>(R.id.practice_action_row).setOnClickListener {
            vocabSwipe.animateSwipeLeft()
        }

        val bankSwipe = findViewById<SwipeCardView>(R.id.bank_swipe_card)
        bankSwipe.preferredDirection = SwipeDirection.RIGHT
        bankSwipe.onSwiped = { showNextBank(animateIn = true) }
        findViewById<ImageButton>(R.id.btn_swipe_right).setOnClickListener {
            bankSwipe.animateSwipeRight()
        }
        findViewById<LinearLayout>(R.id.bank_action_row).setOnClickListener {
            bankSwipe.animateSwipeRight()
        }

        showNextVocab(animateIn = false)
        showNextBank(animateIn = false)
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

    private fun showNextVocab(animateIn: Boolean) {
        currentVocab = vocabDeck.next()
        bindVocabCard(ThemePrefs.get(this))
        animateCardIn(findViewById(R.id.swipe_card), fromLeft = true, animateIn)
    }

    private fun showNextBank(animateIn: Boolean) {
        currentBank = bankDeck.next()
        bindBankCard(ThemePrefs.get(this))
        animateCardIn(findViewById(R.id.bank_swipe_card), fromLeft = false, animateIn)
    }

    private fun animateCardIn(card: SwipeCardView, fromLeft: Boolean, animateIn: Boolean) {
        card.animate().setListener(null)
        if (animateIn) {
            card.alpha = 0f
            card.translationX = if (fromLeft) 72f else -72f
            card.animate()
                .setListener(null)
                .alpha(1f)
                .translationX(0f)
                .setDuration(160)
                .withEndAction { card.unlockGesture() }
                .start()
        } else {
            card.unlockGesture()
        }
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
        val darkPage = theme != AppTheme.COAST

        findViewById<ScrollView>(R.id.main_scroll).setBackgroundColor(pageBg)

        fun styleText(id: Int, color: Int, face: Typeface) {
            findViewById<TextView>(id).apply {
                setTextColor(color)
                typeface = face
            }
        }

        styleText(R.id.main_title, titleColor, wordTypeface)
        styleText(R.id.main_tagline, taglineColor, bodyTypeface)

        val sectionBg =
            if (darkPage) R.drawable.bg_section_panel_dark else R.drawable.bg_section_panel
        val actionBg =
            if (darkPage) R.drawable.bg_action_row else R.drawable.bg_action_row_light

        listOf(
            R.id.section_today,
            R.id.section_practice,
            R.id.section_bank,
            R.id.section_theme,
            R.id.section_help,
        ).forEach { findViewById<LinearLayout>(it).setBackgroundResource(sectionBg) }

        findViewById<LinearLayout>(R.id.practice_action_row).setBackgroundResource(actionBg)
        findViewById<LinearLayout>(R.id.bank_action_row).setBackgroundResource(actionBg)

        styleSectionHeader(
            indexId = R.id.today_index,
            labelId = R.id.today_label,
            hintId = R.id.today_hint,
            barId = R.id.today_accent_bar,
            accent = accent,
            titleColor = titleColor,
            bodyColor = bodyColor,
            wordTypeface = wordTypeface,
            bodyTypeface = bodyTypeface,
        )
        styleSectionHeader(
            indexId = R.id.practice_index,
            labelId = R.id.practice_label,
            hintId = R.id.practice_hint,
            barId = R.id.practice_accent_bar,
            accent = accent,
            titleColor = titleColor,
            bodyColor = bodyColor,
            wordTypeface = wordTypeface,
            bodyTypeface = bodyTypeface,
        )
        styleSectionHeader(
            indexId = R.id.bank_index,
            labelId = R.id.bank_label,
            hintId = R.id.bank_hint,
            barId = R.id.bank_accent_bar,
            accent = accent,
            titleColor = titleColor,
            bodyColor = bodyColor,
            wordTypeface = wordTypeface,
            bodyTypeface = bodyTypeface,
        )
        styleSectionHeader(
            indexId = R.id.theme_index,
            labelId = R.id.theme_label,
            hintId = R.id.theme_hint,
            barId = R.id.theme_accent_bar,
            accent = accent,
            titleColor = titleColor,
            bodyColor = bodyColor,
            wordTypeface = wordTypeface,
            bodyTypeface = bodyTypeface,
        )
        styleSectionHeader(
            indexId = R.id.help_index,
            labelId = R.id.help_label,
            hintId = R.id.main_instructions,
            barId = R.id.help_accent_bar,
            accent = accent,
            titleColor = titleColor,
            bodyColor = bodyColor,
            wordTypeface = wordTypeface,
            bodyTypeface = bodyTypeface,
        )

        styleText(R.id.swipe_left_label, titleColor, bodyTypeface)
        styleText(R.id.swipe_left_hint, bodyColor, bodyTypeface)
        styleText(R.id.swipe_right_label, titleColor, bodyTypeface)
        styleText(R.id.swipe_right_hint, bodyColor, bodyTypeface)

        val chipNormal =
            if (theme == AppTheme.COAST) R.drawable.bg_theme_chip_dark else R.drawable.bg_theme_chip
        val chipSelected =
            if (theme == AppTheme.COAST) R.drawable.bg_theme_chip_dark_selected else R.drawable.bg_theme_chip_selected

        styleThemeChip(R.id.theme_grove, R.id.theme_grove_title, R.id.theme_grove_sub, theme == AppTheme.GROVE, titleColor, taglineColor, bodyTypeface, chipNormal, chipSelected)
        styleThemeChip(R.id.theme_ink, R.id.theme_ink_title, R.id.theme_ink_sub, theme == AppTheme.INK, titleColor, taglineColor, bodyTypeface, chipNormal, chipSelected)
        styleThemeChip(R.id.theme_coast, R.id.theme_coast_title, R.id.theme_coast_sub, theme == AppTheme.COAST, titleColor, taglineColor, bodyTypeface, chipNormal, chipSelected)

        findViewById<LinearLayout>(R.id.preview_card).setBackgroundResource(theme.cardBackground)
        findViewById<LinearLayout>(R.id.swipe_card_inner).setBackgroundResource(theme.cardBackground)
        findViewById<LinearLayout>(R.id.bank_card_inner).setBackgroundResource(theme.cardBackground)

        findViewById<ImageButton>(R.id.btn_swipe_left).background?.mutate()?.setTint(accent)
        findViewById<ImageButton>(R.id.btn_swipe_right).background?.mutate()?.setTint(accent)
        findViewById<View>(R.id.bank_prompt_bar).background?.mutate()?.setTint(accent)

        val today = DailyWord.today(this)
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

        bindVocabCard(theme)
        bindBankCard(theme)
    }

    private fun styleSectionHeader(
        indexId: Int,
        labelId: Int,
        hintId: Int,
        barId: Int,
        accent: Int,
        titleColor: Int,
        bodyColor: Int,
        wordTypeface: Typeface,
        bodyTypeface: Typeface,
    ) {
        findViewById<View>(barId).background = accentBar(accent)
        findViewById<TextView>(indexId).apply {
            setTextColor(accent)
            typeface = bodyTypeface
            background = indexChip(accent)
        }
        findViewById<TextView>(labelId).apply {
            setTextColor(titleColor)
            typeface = wordTypeface
        }
        findViewById<TextView>(hintId).apply {
            setTextColor(bodyColor)
            typeface = bodyTypeface
        }
    }

    private fun accentBar(accent: Int): GradientDrawable =
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 8f * resources.displayMetrics.density
            setColor(accent)
        }

    private fun indexChip(accent: Int): GradientDrawable =
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 10f * resources.displayMetrics.density
            setColor(ColorUtils.setAlphaComponent(accent, 40))
        }

    private fun bindVocabCard(theme: AppTheme) {
        val practice = currentVocab ?: return
        val ink = ContextCompat.getColor(this, theme.ink)
        val inkSoft = ContextCompat.getColor(this, theme.inkSoft)
        val accent = ContextCompat.getColor(this, theme.accent)
        val wordTypeface = ResourcesCompat.getFont(this, theme.wordFont) ?: Typeface.DEFAULT_BOLD
        val bodyTypeface = ResourcesCompat.getFont(this, theme.bodyFont) ?: Typeface.DEFAULT

        findViewById<TextView>(R.id.swipe_badge).apply {
            setTextColor(accent)
            typeface = bodyTypeface
            backgroundTintList = ColorStateList.valueOf(ColorUtils.setAlphaComponent(accent, 36))
        }
        findViewById<TextView>(R.id.swipe_word).apply {
            text = practice.word
            setTextColor(ink)
            typeface = wordTypeface
        }
        findViewById<TextView>(R.id.swipe_meaning).apply {
            text = practice.meaning
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.swipe_root).apply {
            text = "${practice.root} · ${practice.rootMeaning}"
            setTextColor(accent)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.swipe_example).apply {
            text = "“${practice.example}”"
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
    }

    private fun bindBankCard(theme: AppTheme) {
        val item = currentBank ?: return
        val ink = ContextCompat.getColor(this, theme.ink)
        val inkSoft = ContextCompat.getColor(this, theme.inkSoft)
        val accent = ContextCompat.getColor(this, theme.accent)
        val wordTypeface = ResourcesCompat.getFont(this, theme.wordFont) ?: Typeface.DEFAULT_BOLD
        val bodyTypeface = ResourcesCompat.getFont(this, theme.bodyFont) ?: Typeface.DEFAULT

        val kindLabel = when (item.kind) {
            PracticeKind.IDIOM -> getString(R.string.kind_idiom)
            PracticeKind.PHRASAL_VERB -> getString(R.string.kind_phrasal)
            PracticeKind.ONE_WORD -> getString(R.string.kind_one_word)
        }

        findViewById<TextView>(R.id.bank_kind).apply {
            text = kindLabel
            setTextColor(accent)
            typeface = bodyTypeface
            backgroundTintList = ColorStateList.valueOf(ColorUtils.setAlphaComponent(accent, 36))
        }
        findViewById<TextView>(R.id.bank_prompt).apply {
            text = item.prompt
            setTextColor(ink)
            typeface = wordTypeface
        }
        findViewById<TextView>(R.id.bank_meaning).apply {
            text = item.meaning
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.bank_example).apply {
            text = item.example
            setTextColor(inkSoft)
            typeface = bodyTypeface
        }
        findViewById<TextView>(R.id.bank_progress).apply {
            text = bankDeck.progressLabel()
            setTextColor(accent)
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

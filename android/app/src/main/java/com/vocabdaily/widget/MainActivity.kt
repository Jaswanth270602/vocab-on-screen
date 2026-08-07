package com.vocabdaily.widget

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindToday()

        findViewById<Button>(R.id.main_refresh_widget).setOnClickListener {
            VocabWidgetViews.pushAll(this)
            bindToday()
        }
    }

    override fun onResume() {
        super.onResume()
        bindToday()
        VocabWidgetViews.pushAll(this)
    }

    private fun bindToday() {
        val today = DailyWord.today()
        val word = today.word
        findViewById<TextView>(R.id.card_day).text = today.dayLabel
        findViewById<TextView>(R.id.card_count).text = today.dayNumber
        findViewById<TextView>(R.id.card_word).text = word.word
        findViewById<TextView>(R.id.card_meaning).text = word.meaning
        findViewById<TextView>(R.id.card_root).text = "${word.root} · ${word.rootMeaning}"
        findViewById<TextView>(R.id.card_example).text = "“${word.example}”"
    }
}

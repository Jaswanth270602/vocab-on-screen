package com.vocabdaily.widget

/** Shuffled deck for idioms / phrasals / one-word bank. */
class PracticeDeck(
    private val source: List<PracticeItem> = PracticeBank.items,
) {
    private val queue = ArrayDeque<PracticeItem>()
    private var served = 0

    fun next(): PracticeItem {
        check(source.isNotEmpty()) { "Practice bank is empty" }
        if (queue.isEmpty()) {
            queue.addAll(source.shuffled())
            served = 0
        }
        served += 1
        return queue.removeFirst()
    }

    fun progressLabel(): String = "$served / ${source.size}"
}

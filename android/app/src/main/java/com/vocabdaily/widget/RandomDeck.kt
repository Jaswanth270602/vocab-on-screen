package com.vocabdaily.widget

/** Shuffled practice deck — not daily order. Refills and reshuffles when empty. */
class RandomDeck(
    private val source: List<VocabWord> = VocabData.words,
) {
    private val queue = ArrayDeque<VocabWord>()

    fun next(): VocabWord {
        check(source.isNotEmpty()) { "Vocab list is empty" }
        if (queue.isEmpty()) {
            queue.addAll(source.shuffled())
        }
        return queue.removeFirst()
    }
}

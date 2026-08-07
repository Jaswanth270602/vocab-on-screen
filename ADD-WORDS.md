# How to add more words (up to 1000+)

## Where to paste

Open this file:

`android/app/src/main/java/com/vocabdaily/widget/VocabData.kt`

Find the list:

```kotlin
object VocabData {
    val words: List<VocabWord> = listOf(
        VocabWord(1, "inspect", "...", "...", "...", "..."),
        // paste more lines here, before the closing )
    )
}
```

Add new lines **before** the closing `)`. Keep increasing the `id` number (`51`, `52`, …).

The daily word automatically uses `list size`, so 1000 words = 1000-day cycle. No other file needs changing.

---

## Exact line format (copy this for your AI prompt)

Each word is **one Kotlin line**:

```kotlin
VocabWord(ID, "WORD", "MEANING", "ROOT", "ROOT_MEANING", "EXAMPLE SENTENCE."),
```

Rules:

1. Use straight double quotes `"` only  
2. End every line with a comma `,`  
3. Do **not** put a quote `"` inside a string (rewrite the sentence instead)  
4. Keep meaning/example short (widget space is small)  
5. `ID` must be unique and sequential

### Example (good)

```kotlin
VocabWord(51, "benevolent", "kind and generous", "bene", "good", "A benevolent donor funded the library."),
VocabWord(52, "malice", "the wish to harm someone", "mal", "bad", "There was no malice in her joke."),
VocabWord(53, "chronology", "the order of events in time", "chron", "time", "The chronology of the war is clear."),
```

### Example (bad — will break the build)

```kotlin
VocabWord(51, "benevolent", "kind, "generous"", "bene", "good", "He said "hello"."),
```

---

## Prompt to give another AI (paste as-is)

```text
Generate 50 vocabulary words for an Android app.
Output ONLY Kotlin lines, nothing else — no markdown, no numbering outside the ID, no commentary.

Format exactly:
VocabWord(ID, "word", "short meaning", "root", "root meaning", "One short example sentence."),

Rules:
- Start IDs at START_ID and increase by 1
- lowercase headword unless it's a proper noun
- meaning max ~12 words
- example max ~14 words
- no double quotes inside any string
- include Latin/Greek root when possible; if unknown use "—" for root and root meaning
- end each line with a comma

START_ID = 51
```

Then:
1. Copy the AI output  
2. Paste into `VocabData.kt` inside `listOf(`  
3. Rebuild the APK and share the new file with students  

---

## Tips for 1000 words

- Generate in batches of 50–100 (safer than 1000 at once)  
- After each batch, open Android Studio and check the file has no red errors  
- Keep a backup of `VocabData.kt` before big pastes  
- Duplicate words are OK for learning, but unique `id`s are required  

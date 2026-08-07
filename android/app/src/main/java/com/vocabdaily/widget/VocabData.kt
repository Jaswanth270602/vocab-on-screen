package com.vocabdaily.widget

/**
 * Paste more words here. Format guide: ADD-WORDS.md in the project root.
 *
 * VocabWord(ID, "word", "meaning", "root", "root meaning", "Example sentence."),
 */
data class VocabWord(
    val id: Int,
    val word: String,
    val meaning: String,
    val root: String,
    val rootMeaning: String,
    val example: String,
)

object VocabData {
    val words: List<VocabWord> = listOf(
    // --- existing words (keep) ---
    VocabWord(1, "inspect", "to look at something carefully", "spect", "to look", "The mechanic will inspect the engine before the trip."),
    VocabWord(2, "respect", "to admire someone; regard earned by behavior", "spect", "to look", "She earned the team's respect through steady work."),
    VocabWord(3, "prospect", "the chance of something happening; a possible future", "spect", "to look", "The prospect of rain cancelled the picnic."),
    VocabWord(4, "spectacle", "a visually striking display or performance", "spect", "to look", "The fireworks made a dazzling spectacle."),
    VocabWord(5, "spectator", "a person who watches an event", "spect", "to look", "Every spectator stood when the final goal was scored."),
    VocabWord(6, "transport", "to carry people or goods from one place to another", "port", "to carry", "Trucks transport fresh produce across the country."),
    VocabWord(7, "export", "to send goods to another country for sale", "port", "to carry", "The factory exports coffee beans worldwide."),
    VocabWord(8, "import", "to bring goods into a country from abroad", "port", "to carry", "They import olive oil from Spain."),
    VocabWord(9, "portable", "easy to carry or move", "port", "to carry", "She packed a portable charger for the hike."),
    VocabWord(10, "deport", "to force someone to leave a country", "port", "to carry", "The court ordered the authorities to deport him."),
    VocabWord(11, "dictate", "to say something aloud for someone to write; to control", "dict", "to say", "The manager will dictate the new rules tomorrow."),
    VocabWord(12, "predict", "to say what will happen in the future", "dict", "to say", "Experts predict warmer weather this week."),
    VocabWord(13, "contradict", "to say the opposite of what someone else said", "dict", "to say", "Please don't contradict the witness on small details."),
    VocabWord(14, "verdict", "a decision or judgment, especially in court", "dict", "to say", "The jury reached a unanimous verdict."),
    VocabWord(15, "dictionary", "a book that lists words and their meanings", "dict", "to say", "I looked up the rare word in a dictionary."),
    VocabWord(16, "describe", "to say or write what something is like", "scrib", "to write", "Can you describe the stranger you saw?"),
    VocabWord(17, "manuscript", "a document written by hand or an author's draft", "script", "to write", "The museum displayed an ancient manuscript."),
    VocabWord(18, "prescribe", "to officially tell someone to use a medicine or follow a rule", "scrib", "to write", "The doctor will prescribe antibiotics for the infection."),
    VocabWord(19, "transcript", "a written record of spoken words", "script", "to write", "She requested a transcript of the lecture."),
    VocabWord(20, "inscription", "words written or carved on a surface", "script", "to write", "The inscription on the monument was nearly worn away."),
    VocabWord(21, "eject", "to force something out", "ject", "to throw", "Press the button to eject the disk."),
    VocabWord(22, "reject", "to refuse to accept", "ject", "to throw", "The editor decided to reject the draft."),
    VocabWord(23, "project", "to throw forward; a planned piece of work", "ject", "to throw", "They will project the slides onto the wall."),
    VocabWord(24, "inject", "to force a liquid into something; to add", "ject", "to throw", "Nurses inject vaccines with sterile needles."),
    VocabWord(25, "subject", "a topic; a person under authority", "ject", "to throw", "History is my favorite subject this year."),
    VocabWord(26, "construct", "to build or put together", "struct", "to build", "Workers will construct a bridge over the river."),
    VocabWord(27, "destruct", "to destroy (often used in 'self-destruct')", "struct", "to build", "The device will self-destruct after the mission."),
    VocabWord(28, "instruct", "to teach or give directions", "struct", "to build", "Coaches instruct players before every match."),
    VocabWord(29, "structure", "the way parts are arranged; a building", "struct", "to build", "The essay needs a clearer structure."),
    VocabWord(30, "obstruct", "to block or get in the way", "struct", "to build", "Fallen trees obstruct the mountain path."),
    VocabWord(31, "attract", "to pull toward; to draw interest", "tract", "to pull", "Bright colors attract hummingbirds to the garden."),
    VocabWord(32, "extract", "to pull out or remove", "tract", "to pull", "Dentists extract teeth only when necessary."),
    VocabWord(33, "contract", "to shrink; a legal agreement", "tract", "to pull", "Muscles contract when you lift a weight."),
    VocabWord(34, "distract", "to pull attention away", "tract", "to pull", "Phone alerts distract students during class."),
    VocabWord(35, "retract", "to pull back; to take back a statement", "tract", "to pull", "The cat can retract its claws."),
    VocabWord(36, "transform", "to change shape or character completely", "form", "shape", "Practice can transform timid speakers into confident ones."),
    VocabWord(37, "reform", "to improve by changing", "form", "shape", "Leaders hope to reform the school lunch program."),
    VocabWord(38, "conform", "to follow rules or match a standard", "form", "shape", "All bags must conform to airline size limits."),
    VocabWord(39, "deform", "to spoil the shape of something", "form", "shape", "Heat can deform cheap plastic bottles."),
    VocabWord(40, "formulate", "to create or express carefully", "form", "shape", "Scientists formulate a hypothesis before testing."),
    VocabWord(41, "credit", "praise; belief that someone will pay later", "cred", "to believe", "She deserves credit for solving the bug."),
    VocabWord(42, "incredible", "hard to believe; amazing", "cred", "to believe", "The view from the ridge was incredible."),
    VocabWord(43, "credible", "believable; trustworthy", "cred", "to believe", "We need a credible source for that claim."),
    VocabWord(44, "credentials", "proof of skills or identity", "cred", "to believe", "Bring your credentials to the interview."),
    VocabWord(45, "discredit", "to harm someone's reputation; to cast doubt", "cred", "to believe", "Rumors were spread to discredit the candidate."),
    VocabWord(46, "biography", "the written story of someone's life", "graph", "to write", "I read a biography of Marie Curie."),
    VocabWord(47, "autograph", "a person's own signature", "graph", "to write", "Fans waited for the singer's autograph."),
    VocabWord(48, "telegraph", "an old system for sending messages over wires", "graph", "to write", "News once traveled by telegraph across oceans."),
    VocabWord(49, "paragraph", "a group of sentences about one idea", "graph", "to write", "Start a new paragraph when the topic shifts."),
    VocabWord(50, "graphic", "related to visual art; vividly detailed", "graph", "to write", "The report included a clear graphic of the data."),

    // --- paste new words below (see ADD-WORDS.md) ---
    // VocabWord(51, "word", "meaning", "root", "root meaning", "Example sentence."),
    )
}

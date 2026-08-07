import type { VocabWord } from "@/data/vocab";

type Props = {
  word: VocabWord;
  dayLabel?: string;
  dayNumber?: string;
  featured?: boolean;
};

export function VocabCard({ word, dayLabel, dayNumber, featured = false }: Props) {
  return (
    <article className={`vocab-card ${featured ? "vocab-card--featured" : ""}`}>
      <header className="vocab-card__meta">
        {dayLabel ? <span className="vocab-card__day">{dayLabel}</span> : null}
        {dayNumber ? <span className="vocab-card__count">{dayNumber}</span> : null}
      </header>

      <p className="vocab-card__eyebrow">Word of the day</p>
      <h2 className="vocab-card__word">{word.word}</h2>
      <p className="vocab-card__meaning">{word.meaning}</p>

      <div className="vocab-card__root">
        <span className="vocab-card__root-label">Root</span>
        <p className="vocab-card__root-word">
          <em>{word.root}</em>
          <span aria-hidden="true"> · </span>
          {word.rootMeaning}
        </p>
      </div>

      <blockquote className="vocab-card__example">
        <span className="vocab-card__example-label">Example</span>
        <p>“{word.example}”</p>
      </blockquote>
    </article>
  );
}

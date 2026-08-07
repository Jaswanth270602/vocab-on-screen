"use client";

import { useMemo, useState } from "react";
import { VOCAB, getRoots, wordsByRoot } from "@/data/vocab";
import { VocabCard } from "@/components/VocabCard";

export function RootBrowser() {
  const roots = useMemo(() => getRoots(), []);
  const [root, setRoot] = useState(roots[0] ?? "spect");
  const words = wordsByRoot(root);
  const rootMeaning = words[0]?.rootMeaning ?? "";

  return (
    <section className="browser" aria-labelledby="browse-heading">
      <div className="browser__intro">
        <h2 id="browse-heading">Browse by root</h2>
        <p>
          {VOCAB.length} words · same order for everyone · today advances one card each UTC day
        </p>
      </div>

      <div className="root-chips" role="tablist" aria-label="Word roots">
        {roots.map((r) => (
          <button
            key={r}
            type="button"
            role="tab"
            aria-selected={r === root}
            className={`root-chip ${r === root ? "root-chip--active" : ""}`}
            onClick={() => setRoot(r)}
          >
            {r}
          </button>
        ))}
      </div>

      <p className="browser__root-meaning">
        <em>{root}</em> means “{rootMeaning}”
      </p>

      <div className="browser__grid">
        {words.map((word) => (
          <VocabCard key={word.id} word={word} />
        ))}
      </div>
    </section>
  );
}

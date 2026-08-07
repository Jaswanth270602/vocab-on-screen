import { InstallHint } from "@/components/InstallHint";
import { RootBrowser } from "@/components/RootBrowser";
import { VocabCard } from "@/components/VocabCard";
import { dayNumberLabel, formatDayLabel, getTodaysWord } from "@/lib/daily";

export const dynamic = "force-dynamic";

export default function HomePage() {
  const today = getTodaysWord();
  const dayLabel = formatDayLabel();
  const dayNumber = dayNumberLabel();

  return (
    <main className="shell">
      <header className="brand">
        <h1 className="brand__mark">
          Vocab <span>Daily</span>
        </h1>
        <p className="brand__tag">Root words</p>
      </header>

      <section className="hero" aria-label="Today's vocabulary">
        <p className="hero__lede">
          One shared word each day — same card for everyone, rotating through fifty root-grouped
          words.
        </p>
        <VocabCard word={today} dayLabel={dayLabel} dayNumber={dayNumber} featured />
      </section>

      <InstallHint />
      <RootBrowser />

      <footer className="site-foot">Updates at UTC midnight · Add to Home Screen to keep it close</footer>
    </main>
  );
}

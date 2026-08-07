import { VOCAB, type VocabWord } from "@/data/vocab";

/** UTC midnight epoch used so every user sees the same word on a given calendar day. */
const EPOCH_UTC = Date.UTC(2026, 0, 1);

export function utcDayIndex(date: Date = new Date()): number {
  const today = Date.UTC(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate());
  const days = Math.floor((today - EPOCH_UTC) / 86_400_000);
  const n = VOCAB.length;
  return ((days % n) + n) % n;
}

export function getTodaysWord(date: Date = new Date()): VocabWord {
  return VOCAB[utcDayIndex(date)]!;
}

export function formatDayLabel(date: Date = new Date()): string {
  return new Intl.DateTimeFormat("en-US", {
    weekday: "long",
    month: "short",
    day: "numeric",
    timeZone: "UTC",
  }).format(date);
}

export function dayNumberLabel(date: Date = new Date()): string {
  const index = utcDayIndex(date);
  return `${index + 1} / ${VOCAB.length}`;
}

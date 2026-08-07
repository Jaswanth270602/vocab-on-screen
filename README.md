# Vocab Daily

A mobile-first PWA that shows **one shared vocabulary word per day** — meaning, Latin/Greek root, root meaning, and an example sentence.

Deploy on [Vercel](https://vercel.com), open on your phone, then **Add to Home Screen**.

## Features

- 50 words grouped by root (`spect`, `port`, `dict`, …)
- Same word for everyone each UTC day
- Card UI + browse-by-root
- Installable PWA (standalone)

> Note: real moveable lock/home-screen *widgets* need a native app. This ships the web/PWA version you can test on Vercel first.

## Local

```bash
npm install
npm run dev
```

Open [http://localhost:3000](http://localhost:3000).

## Deploy to Vercel

1. Push this folder to GitHub, or from the project directory run:

```bash
npx vercel
```

2. On your phone, open the Vercel URL in Safari (iOS) or Chrome (Android).
3. Install:
   - **iOS:** Share → Add to Home Screen
   - **Android:** Menu → Install app / Add to Home screen

## How the daily word works

Day index is derived from UTC date starting `2026-01-01`, then modulo 50, so every user sees the same card that day.

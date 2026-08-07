"use client";

import { useEffect, useState } from "react";

type BeforeInstallPromptEvent = Event & {
  prompt: () => Promise<void>;
  userChoice: Promise<{ outcome: "accepted" | "dismissed" }>;
};

export function InstallHint() {
  const [deferred, setDeferred] = useState<BeforeInstallPromptEvent | null>(null);
  const [isIos, setIsIos] = useState(false);
  const [standalone, setStandalone] = useState(true);
  const [dismissed, setDismissed] = useState(false);

  useEffect(() => {
    const ua = window.navigator.userAgent;
    const ios = /iPad|iPhone|iPod/.test(ua) || (navigator.platform === "MacIntel" && navigator.maxTouchPoints > 1);
    const isStandalone =
      window.matchMedia("(display-mode: standalone)").matches ||
      ("standalone" in navigator && Boolean((navigator as Navigator & { standalone?: boolean }).standalone));

    setIsIos(ios);
    setStandalone(isStandalone);

    const stored = window.localStorage.getItem("vocab-install-dismissed");
    if (stored === "1") setDismissed(true);

    const onPrompt = (e: Event) => {
      e.preventDefault();
      setDeferred(e as BeforeInstallPromptEvent);
    };
    window.addEventListener("beforeinstallprompt", onPrompt);
    return () => window.removeEventListener("beforeinstallprompt", onPrompt);
  }, []);

  if (standalone || dismissed) return null;

  const hide = () => {
    setDismissed(true);
    window.localStorage.setItem("vocab-install-dismissed", "1");
  };

  const install = async () => {
    if (!deferred) return;
    await deferred.prompt();
    await deferred.userChoice;
    setDeferred(null);
  };

  return (
    <div className="install-hint" role="region" aria-label="Install tip">
      <div>
        <p className="install-hint__title">Keep today’s word handy</p>
        <p className="install-hint__text">
          {isIos
            ? "On iPhone: tap Share → Add to Home Screen. Open it like a mini widget app."
            : deferred
              ? "Install Vocab Daily on your home screen for a one-tap daily card."
              : "On Android: open the browser menu → Install app / Add to Home screen."}
        </p>
      </div>
      <div className="install-hint__actions">
        {deferred ? (
          <button type="button" className="btn btn--primary" onClick={install}>
            Install
          </button>
        ) : null}
        <button type="button" className="btn btn--ghost" onClick={hide}>
          Dismiss
        </button>
      </div>
    </div>
  );
}

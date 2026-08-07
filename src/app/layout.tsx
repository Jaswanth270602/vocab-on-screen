import type { Metadata, Viewport } from "next";
import { Fraunces, Outfit } from "next/font/google";
import { ServiceWorkerRegister } from "@/components/ServiceWorkerRegister";
import "./globals.css";

const fraunces = Fraunces({
  variable: "--font-fraunces",
  subsets: ["latin"],
  weight: ["500", "600", "700"],
});

const outfit = Outfit({
  variable: "--font-outfit",
  subsets: ["latin"],
  weight: ["400", "500", "600", "700"],
});

export const metadata: Metadata = {
  title: "Vocab Daily",
  description:
    "One root-based vocabulary word every day — meaning, root, and example on a clean card.",
  applicationName: "Vocab Daily",
  appleWebApp: {
    capable: true,
    statusBarStyle: "black-translucent",
    title: "Vocab Daily",
  },
  formatDetection: {
    telephone: false,
  },
  openGraph: {
    title: "Vocab Daily",
    description: "One vocabulary card a day, shared by everyone.",
    type: "website",
  },
};

export const viewport: Viewport = {
  themeColor: "#12201c",
  width: "device-width",
  initialScale: 1,
  maximumScale: 1,
  viewportFit: "cover",
};

export default function RootLayout({ children }: LayoutProps<"/">) {
  return (
    <html lang="en" className={`${fraunces.variable} ${outfit.variable} h-full`}>
      <body className="min-h-full antialiased">
        {children}
        <ServiceWorkerRegister />
      </body>
    </html>
  );
}

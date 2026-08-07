import { ImageResponse } from "next/og";

export const size = { width: 512, height: 512 };
export const contentType = "image/png";

export default function Icon() {
  return new ImageResponse(
    (
      <div
        style={{
          width: "100%",
          height: "100%",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          background: "linear-gradient(145deg, #183028 0%, #0d1714 100%)",
          color: "#f0c089",
          fontSize: 220,
          fontWeight: 700,
          letterSpacing: "-0.06em",
          fontFamily: "Georgia, serif",
        }}
      >
        V
      </div>
    ),
    { ...size },
  );
}

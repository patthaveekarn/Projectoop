import React from "react";
import HexTile from "./HexTile";

const GRID_ROWS = 8;
const GRID_COLS = 8;
const HEX_SIZE = 60; // ✅ ขนาดของ Hexagon
const HEX_SPACING = -5; // ✅ ปรับช่องว่างระหว่างแถว

const HexMap: React.FC = () => {
  return (
      <div className="hex-grid">
        {Array.from({ length: GRID_ROWS }).map((_, row) => (
            <div
                className="hex-row"
                key={row}
                style={{
                  marginLeft: row % 2 === 1 ? `${HEX_SIZE / 2}px` : "0px", // ✅ แถวเลขคี่เยื้องขวา
                  marginTop: `${HEX_SPACING}px`, // ✅ ลดช่องว่างแถวให้ Hexagon ชิดกัน
                }}
            >
              {Array.from({ length: GRID_COLS }).map((_, col) => (
                  <HexTile key={`${row}-${col}`} row={row + 1} col={col + 1} />
              ))}
            </div>
        ))}
      </div>
  );
};

export default HexMap;
import React from "react";

interface HexTileProps {
    row: number;
    col: number;
}

const HexTile: React.FC<HexTileProps> = ({ row, col }) => {
    return (
        <div className="hex-tile">
            {/* ลบตัวเลขออก ถ้าต้องการแค่ Hexagon เปล่า */}
        </div>
    );
};

export default HexTile;
"use client";

import React from 'react';
import "../styles/Howtoplay.css";
import {useRouter} from "next/navigation";

const Kombat: React.FC = () => {
    const router = useRouter();

    return (
        <div className="howtoplay-container">
            <div className="howtoplay-modal">
                <div className="howtoplay-content">
                    <div className="howtoplay-title">วิธีการเล่นเกม KOMBAT</div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">เริ่มเกม:</div>
                        <ul>
                            <li>เลือกผู้เล่นและจะวางประเภทเริ่มต้น</li>
                            <li>สร้างทีมเกมใหม่และเล่นในพื้นที่ว่าง</li>
                            <li>เปิดทีมเริ่มต้นผ่านเพื่อพอ</li>
                        </ul>
                    </div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">สร้างทีมเริ่มเกม:</div>
                        <ul>
                            <li>สร้างทีมเริ่มต้นโดยเฉพาะทางวาง</li>
                            <li>เปิดทีมเริ่มต้นผ่านเพื่อพอ</li>
                        </ul>
                    </div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">เงื่อนไขทีมเกม:</div>
                        <ul>
                            <li>เลือกทีมและเลือกทีพรมฟะเคลื่อนที่</li>
                        </ul>
                    </div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">วิธีเล่น:</div>
                        <ul>
                            <li>เมื่อยึดพื้นที่และสามารถเคลื่อนที่ได้</li>
                            <li>เลือกทีมที่มีประสิทธิภาพที่สุดในการต่อสู้</li>
                        </ul>
                    </div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">จบเกม:</div>
                        <ul>
                            <li>เกมสิ้นสุดลงตามคุณสมบัติที่กำหนด</li>
                            <li>ผู้เล่นสามารถชนะได้ตามเงื่อนไข HP ลดลง หรือพื้นที่ที่กำหนด</li>
                        </ul>
                    </div>
                </div>

                <div className="howtoplay-button-container">
                    <button className="back-button" onClick={() => router.push("/")}>
                        Back to Home
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Kombat;
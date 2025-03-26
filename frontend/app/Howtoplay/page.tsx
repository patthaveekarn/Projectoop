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
                            <li>เลือกผู้เล่นและงบประมาณเริ่มต้น</li>
                        </ul>
                    </div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">สร้างมินเนียน:</div>
                        <ul>
                            <li>สร้างมินเนียนใหม่ในสนามที่ว่าง</li>
                            <li>เมื่อมีงบประมาณเพียงพอ</li>
                        </ul>
                    </div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">เคลื่อนไหวมินเนียน:</div>
                        <ul>
                            <li>เลือกมินเนียนและเลือกทิศทางที่จะเคลื่อนที่</li>
                            <li>เมื่อมินเนียนของคุณอยู่ใกล้ฝ่ายตรงข้าม, ให้เลือกโจมตี</li>
                        </ul>
                    </div>

                    <div className="howtoplay-section">
                        <div className="howtoplay-subtitle">จบเกม:</div>
                        <ul>
                            <li>เกมจบเมื่อฝ่ายใดฝ่ายหนึ่งหมดมินเนียนหรือครบจำนวนเทิร์น</li>
                            <li>ผู้ชนะคือผู้ที่มีมินเนียนเหลือ, HP สูงสุด, หรืองบประมาณมากที่สุด</li>
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
"use client";

import React from "react";
import { useRouter } from "next/navigation";
import "./styles/Home.css"; // Import CSS

const Home: React.FC = () => {
    const router = useRouter();

    return (
        <div className="home-container">
            <h1 className="game-title">KOMBAT GAME</h1>

            <button className="play-button" onClick={() => router.push("/ChooseMode")}>
                Play
            </button>

            <button className="how-to-play-button" onClick={() => router.push("/Howtoplay")}>
                How to Play
            </button>
        </div>
    );
};

export default Home;
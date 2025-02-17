"use client";

import React from 'react';
import './globals.css'

export default function Home() {
    return (

        <div className="flex flex-col items-center justify-center h-screen hexagon-bg relative overflow-hidden">

            <h1 className="text-4xl font-bold mb-6" >KOMBAT</h1>

            <button className="px-6 py-3 text-lg font-semibold text-white rounded-full mb-4 bg-gradient-to-r from-indigo-500 to-blue-400 hover:scale-105 transition" onClick={() => alert('Game Started!')}>
                START
            </button>

            <button className="px-6 py-3 text-lg font-semibold text-white rounded-full bg-gradient-to-r from-red-400 to-orange-400 hover:scale-105 transition" onClick={() => alert('Instructions: Capture the opponent\'s pieces!')}>
                How to play
            </button>

            <div className="absolute bottom-5 w-full flex justify-between px-10">
                <img src="https://upload.wikimedia.org/wikipedia/commons/e/e3/Chess_nlt45.svg" alt="Knight" className="w-20" />
                <img src="https://upload.wikimedia.org/wikipedia/commons/7/7e/Chess_blp45.svg" alt="Pawn" className="w-20" />
            </div>

        </div>
    );
}

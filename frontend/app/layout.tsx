"use client"; // ใช้ถ้าไฟล์นี้ต้องการ Redux หรือ Hooks

import { Provider } from "react-redux";
import { store } from "./stores/store";

export default function RootLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="en">
        <body>
        <Provider store={store}>
            {children}
        </Provider>
        </body>
        </html>
    );
}
package project.kombat.parser;

// อินเตอร์เฟซนี้กำหนดวิธีการทำงานของตัวแยกคำ (tokenizer)
// ทุกคลาสที่ implement อินเตอร์เฟซนี้ต้องมีเมธอดเหล่านี้
public interface Tokenizer {
    // เช็คว่ายังมีคำ (token) ที่ยังไม่ได้อ่านเหลืออยู่หรือไม่
    boolean hasNextToken();

    // ดูคำถัดไปโดยไม่เลื่อนตำแหน่ง (ไม่ consume)
    // ถ้าไม่มีคำเหลือจะ throw SyntaxError
    String peek();

    // ดูคำที่อยู่ถัดไปอีกตำแหน่ง (ข้ามคำถัดไปไปอีก 1 คำ) โดยไม่เลื่อนตำแหน่ง
    // ถ้าไม่มีคำเหลือจะ throw SyntaxError
    String peekNext();

    // อ่านคำถัดไปและเลื่อนตำแหน่งไปด้วย
    // ถ้าไม่มีคำเหลือจะ throw SyntaxError
    String consume();

    // เช็คว่าคำถัดไปตรงกับ regex ที่ให้มาหรือไม่
    // ถ้าตรงจะ consume คำนั้นและคืน true
    // ถ้าไม่ตรงจะไม่ consume และคืน false
    boolean consume(String regex);

    // คืนค่าเลขบรรทัดปัจจุบันที่กำลังอ่านอยู่
    int getNewline();
}

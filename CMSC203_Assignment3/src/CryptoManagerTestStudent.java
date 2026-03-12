import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CryptoManagerTestStudent {

    @Test
    public void testStudentIsStringInBounds() {
        assertTrue(CryptoManager.isStringInBounds("HELLO WORLD"));
        assertFalse(CryptoManager.isStringInBounds("HELLO`"));
    }

    @Test
    public void testStudentCaesarEncryptionDecryption() {
        String text = "HELLO WORLD";
        int key = 5;

        String encrypted = CryptoManager.caesarEncryption(text, key);
        String decrypted = CryptoManager.caesarDecryption(encrypted, key);

        assertEquals(text, decrypted);
    }

    @Test
    public void testStudentVigenereEncryptionDecryption() {
        String text = "COMPUTER SCIENCE";
        String key = "KEY";

        String encrypted = CryptoManager.vigenereEncryption(text, key);
        String decrypted = CryptoManager.vigenereDecryption(encrypted, key);

        assertEquals(text, decrypted);
    }

    @Test
    public void testStudentPlayfairEncryptionDecryption() {
        String text = "DATA";
        String key = "SECRET";

        String encrypted = CryptoManager.playfairEncryption(text, key);
        String decrypted = CryptoManager.playfairDecryption(encrypted, key);

        assertEquals(text, decrypted);
    }

    @Test
    public void testStudentCaesarBoundaryWrap() {
        assertEquals(" ", CryptoManager.caesarEncryption("_", 1));
        assertEquals("_", CryptoManager.caesarDecryption(" ", 1));
    }
}
/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches.
 *
 * The first approach is called the Vigenere Cipher. Vigenere encryption
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 *
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph)
 * at a time instead of just one.
 *
 * The third approach is Caesar Cipher. It is a simple replacement cypher.
 *
 * @author Huseyin Aygun
 * @version 8/3/2025
 */
public class CryptoManager {

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;

    // 64 total characters from ASCII 32 (' ') to ASCII 95 ('_')
    private static final String ALPHABET64 =
            " ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_";

    private static final String ERROR_STRING =
            "The selected string is not in bounds, Try again.";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (plainText.charAt(i) < LOWER_RANGE || plainText.charAt(i) > UPPER_RANGE) {
                return false;
            }
        }
        return true;
    }

    public static String vigenereEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText)) {
            return ERROR_STRING;
        }

        String encrypted = "";
        int keyIndex = 0;

        for (int i = 0; i < plainText.length(); i++) {
            char current = plainText.charAt(i);
            char keyChar = key.charAt(keyIndex % key.length());

            int shift = getVigenereShift(keyChar);
            int value = current + shift;

            while (value > UPPER_RANGE) {
                value -= RANGE;
            }

            encrypted += (char) value;
            keyIndex++;
        }

        return encrypted;
    }

    public static String vigenereDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText)) {
            return ERROR_STRING;
        }

        String decrypted = "";
        int keyIndex = 0;

        for (int i = 0; i < encryptedText.length(); i++) {
            char current = encryptedText.charAt(i);
            char keyChar = key.charAt(keyIndex % key.length());

            int shift = getVigenereShift(keyChar);
            int value = current - shift;

            while (value < LOWER_RANGE) {
                value += RANGE;
            }

            decrypted += (char) value;
            keyIndex++;
        }

        return decrypted;
    }

    public static String playfairEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText)) {
            return ERROR_STRING;
        }

        char[][] matrix = buildPlayfairMatrix(key);
        String prepared = preparePlayfairText(plainText);
        String encrypted = "";

        for (int i = 0; i < prepared.length(); i += 2) {
            char first = prepared.charAt(i);
            char second = prepared.charAt(i + 1);

            int[] pos1 = findPosition(matrix, first);
            int[] pos2 = findPosition(matrix, second);

            if (pos1[0] == pos2[0]) {
                encrypted += matrix[pos1[0]][(pos1[1] + 1) % 8];
                encrypted += matrix[pos2[0]][(pos2[1] + 1) % 8];
            } else if (pos1[1] == pos2[1]) {
                encrypted += matrix[(pos1[0] + 1) % 8][pos1[1]];
                encrypted += matrix[(pos2[0] + 1) % 8][pos2[1]];
            } else {
                encrypted += matrix[pos1[0]][pos2[1]];
                encrypted += matrix[pos2[0]][pos1[1]];
            }
        }

        return encrypted;
    }

    public static String playfairDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText)) {
            return ERROR_STRING;
        }

        char[][] matrix = buildPlayfairMatrix(key);
        String prepared = preparePlayfairEncryptedText(encryptedText);
        String decrypted = "";

        for (int i = 0; i < prepared.length(); i += 2) {
            char first = prepared.charAt(i);
            char second = prepared.charAt(i + 1);

            int[] pos1 = findPosition(matrix, first);
            int[] pos2 = findPosition(matrix, second);

            if (pos1[0] == pos2[0]) {
                decrypted += matrix[pos1[0]][(pos1[1] + 7) % 8];
                decrypted += matrix[pos2[0]][(pos2[1] + 7) % 8];
            } else if (pos1[1] == pos2[1]) {
                decrypted += matrix[(pos1[0] + 7) % 8][pos1[1]];
                decrypted += matrix[(pos2[0] + 7) % 8][pos2[1]];
            } else {
                decrypted += matrix[pos1[0]][pos2[1]];
                decrypted += matrix[pos2[0]][pos1[1]];
            }
        }

        return removeInsertedX(decrypted);
    }

    public static String caesarEncryption(String plainText, int key) {
        if (!isStringInBounds(plainText)) {
            return ERROR_STRING;
        }

        String encrypted = "";
        int shift = key % RANGE;

        for (int i = 0; i < plainText.length(); i++) {
            int value = plainText.charAt(i) + shift;

            while (value > UPPER_RANGE) {
                value -= RANGE;
            }

            encrypted += (char) value;
        }

        return encrypted;
    }

    public static String caesarDecryption(String encryptedText, int key) {
        if (!isStringInBounds(encryptedText)) {
            return ERROR_STRING;
        }

        String decrypted = "";
        int shift = key % RANGE;

        for (int i = 0; i < encryptedText.length(); i++) {
            int value = encryptedText.charAt(i) - shift;

            while (value < LOWER_RANGE) {
                value += RANGE;
            }

            decrypted += (char) value;
        }

        return decrypted;
    }

    private static int getVigenereShift(char keyChar) {
        if (keyChar >= 'A' && keyChar <= 'Z') {
            return keyChar - 'A';
        }
        return 0;
    }

    private static char[][] buildPlayfairMatrix(String key) {
        char[][] matrix = new char[8][8];
        String unique = "";
        String combined = key + ALPHABET64;

        for (int i = 0; i < combined.length(); i++) {
            char current = combined.charAt(i);

            if (ALPHABET64.indexOf(current) != -1 && unique.indexOf(current) == -1) {
                unique += current;
            }
        }

        int index = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                matrix[row][col] = unique.charAt(index);
                index++;
            }
        }

        return matrix;
    }

    private static String preparePlayfairText(String text) {
        String cleaned = "";

        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (ALPHABET64.indexOf(current) != -1) {
                cleaned += current;
            }
        }

        String prepared = "";
        int i = 0;

        while (i < cleaned.length()) {
            char first = cleaned.charAt(i);

            if (i + 1 < cleaned.length()) {
                char second = cleaned.charAt(i + 1);

                if (first == second) {
                    prepared += first;
                    prepared += 'X';
                    i++;
                } else {
                    prepared += first;
                    prepared += second;
                    i += 2;
                }
            } else {
                prepared += first;
                prepared += 'X';
                i++;
            }
        }

        return prepared;
    }

    private static String preparePlayfairEncryptedText(String text) {
        String cleaned = "";

        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (ALPHABET64.indexOf(current) != -1) {
                cleaned += current;
            }
        }

        if (cleaned.length() % 2 != 0) {
            cleaned += 'X';
        }

        return cleaned;
    }

    private static int[] findPosition(char[][] matrix, char target) {
        int[] position = new int[2];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (matrix[row][col] == target) {
                    position[0] = row;
                    position[1] = col;
                    return position;
                }
            }
        }

        return position;
    }

    private static String removeInsertedX(String text) {
        String result = "";

        for (int i = 0; i < text.length(); i++) {
            if (i > 0 && i < text.length() - 1
                    && text.charAt(i) == 'X'
                    && text.charAt(i - 1) == text.charAt(i + 1)) {
                continue;
            }
            result += text.charAt(i);
        }

        if (result.endsWith("X")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }
}
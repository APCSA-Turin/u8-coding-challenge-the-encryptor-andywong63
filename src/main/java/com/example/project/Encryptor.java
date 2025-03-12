package com.example.project;
import java.util.ArrayList;
import java.util.Arrays;

public class Encryptor {
    
    public static int determineColumns(int messageLen, int rows) {
        int columns = (int) Math.ceil((double) messageLen / rows);
        if (columns == 0) return 1;
        else return columns;
    }
    
    public static String[][] generateEncryptArray(String message, int rows) {
        int columns = determineColumns(message.length(), rows);
        String[][] array = new String[rows][columns];
        int charIndex = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (charIndex >= message.length()) {
                    array[i][j] = "=";
                } else {
                    array[i][j] = message.substring(charIndex, charIndex + 1);
                    charIndex++;
                }
            }
        }
        return array;
    }

    public static String encryptMessage(String message, int rows) {
        String encrypted = "";
        String[][] encryptArray = generateEncryptArray(message, rows);
        for (int i = encryptArray[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < encryptArray.length; j++) {
                encrypted += encryptArray[j][i];
            }
        }
        return encrypted;
    }

    public static String decryptMessage(String encryptedMessage, int rows) {
        double remainder = (double) encryptedMessage.length() % rows;
        if (remainder != 0) {
            // If rows are incorrect, pad message with enough "=" so it doesn't error
            for (int i = 0; i < (rows - remainder); i++) {
                encryptedMessage += "=";
            }
        }

        int columns = determineColumns(encryptedMessage.length(), rows);
        String[][] decryptArray = new String[rows][columns];
        int currentRow = 0;
        int currentCol = columns - 1;
        for (int i = 0; i < encryptedMessage.length(); i++) {
            String character = encryptedMessage.substring(i, i + 1);
            decryptArray[currentRow][currentCol] = character;
            currentRow++;
            if (currentRow >= rows) {
                currentCol--;
                currentRow = 0;
            }
        }
        String decrypted = "";
        for (String[] row : decryptArray) {
            for (String character : row) {
                if (!character.equals("=")) decrypted += character;
            }
        }
        return decrypted;
    }
}
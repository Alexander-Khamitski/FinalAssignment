package com.teachmeskills.finalAssignment.encryptor;

import com.teachmeskills.finalAssignment.consts.EncryptorConstants;

import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors;

public final class Encryptor {

    public static String encrypt(String value) {
        String encryptedString = Base64.getEncoder().encodeToString(value.getBytes());
        String saltedString = salt(encryptedString);
        return saltedString;
    }

    public static String decrypt(String value) {
        String desaltedString = desalt(value);
        return new String(Base64.getDecoder().decode(desaltedString));
    }

    private static String salt(String input) {
        String symbols = EncryptorConstants.SALTED_SYMBOLS;
        String salt = new Random()
                .ints(input.length(), 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        String[] inputArray = input.split("");
        String[] saltArray = salt.split("");
        String saltedString = "";
        for (int i = 0; i < input.length(); i++) {
            saltedString += inputArray[i] + saltArray[i];
        }
        return saltedString;
    }

    private static String desalt(String input) {
        char[] inputArray = input.toCharArray();
        String desaltedString = "";
        for (int i = 0; i < input.length(); i++) {
            if (i % 2 == 0) {
                desaltedString += inputArray[i];
            }
        }
        return desaltedString;
    }
}

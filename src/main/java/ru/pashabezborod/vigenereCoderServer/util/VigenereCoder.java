package ru.pashabezborod.vigenereCoderServer.util;

import ru.pashabezborod.vigenereCoderServer.model.User.User;

public class VigenereCoder {

    public static String encrypt(String password, User user) {
        String userCrypt = makeCrypt(user);
        StringBuilder codedString = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) > 32 && password.charAt(i) < 127) {
                codedString.append(codeEngSym(password.charAt(i), userCrypt.charAt(i % userCrypt.length())));
            } else codedString.append(password.charAt(i));
        }
        return codedString.toString();
    }

    public static String decrypt(String password, User user) {
        String userCrypt = makeCrypt(user);
        StringBuilder encodedString = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) > 32 && password.charAt(i) < 127)
                encodedString.append(unCodeEngSym(password.charAt(i), userCrypt.charAt(i % userCrypt.length())));
            else encodedString.append(password.charAt(i));
        }
        return encodedString.toString();
    }

    private static char codeEngSym(char p, char k) {
        return (char) ((p - 33 + k - 33) % 94 + 33);
    }

    private static char unCodeEngSym(char p, char k) {
        return (char) ((p - k + 94) % 94 + 33);
    }

    private static String makeCrypt(User user) {
        return String.valueOf(user.getName().hashCode() * 291).repeat(5);
    }
}

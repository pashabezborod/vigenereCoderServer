package ru.pashabezborod.vigenereCoderServer.util;

import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {
    public static List<String> checkPassword(String password) {
        List<String> result = new ArrayList<>();
        if (password == null || password.isEmpty())
            result.add("Необходимо указать пароль");
        else if (password.length() < 5)
            result.add("Пароль должен быть более 4 символов");
        else if (password.chars().anyMatch(c -> c == ' '))
            result.add("Пароль не может содержать пробелы");
            return result;
    }

    public static List<String> checkName(String name) {
        List<String> result = new ArrayList<>();
        if (name == null || name.isEmpty())
            result.add("Необходимо указать имя пользователя");
        else if (name.length() < 5)
            result.add("Имя должно быть более 4 символов");
        else if (name.chars().anyMatch(c -> !Character.isLetterOrDigit(c)))
            result.add("Имя может содержать только латинские буквы или цифры");
        return result;
    }

    public static List<String> checkPasswordName(String name) {
        List<String> result = new ArrayList<>();
        if (name == null || name.isEmpty())
            result.add("Необходимо указать название пароля");
        else if (name.length() < 5)
            result.add("Название пароля должно быть более 4 символов");
        else if (name.chars().anyMatch(c -> !Character.isLetterOrDigit(c) && c != '.'))
            result.add("Название пароля может содержать только латинские буквы или цифры");
        return result;
    }
}

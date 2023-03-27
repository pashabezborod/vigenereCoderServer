package ru.pashabezborod.vigenereCoderServer.model.Password;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.Field;
import ru.pashabezborod.vigenereCoderServer.model.User.User;
import ru.pashabezborod.vigenereCoderServer.util.ObjectUtils;
import ru.pashabezborod.vigenereCoderServer.util.VigenereCoder;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class PasswordRq {
    private String cookie, name, password;

    public Map<Field, String> checkFields() {
        Map<Field, String> result = new HashMap<>();
        for (String error : ObjectUtils.checkPasswordName(name))
            result.put(Field.PASSWORD_NAME, error);
        for (String error : ObjectUtils.checkPassword(password))
            result.put(Field.PASSWORD, error);
        return result;
    }

    public Password getPassword(User user) {
        return new Password(user.getId(),
                name,
                VigenereCoder.encrypt(password, user),
                String.valueOf(password.hashCode()));
    }

    public PasswordKey getKey(Integer userId) {
        return new PasswordKey(userId, name);
    }
}

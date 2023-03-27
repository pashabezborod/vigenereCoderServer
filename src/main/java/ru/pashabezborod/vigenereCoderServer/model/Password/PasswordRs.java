package ru.pashabezborod.vigenereCoderServer.model.Password;

import lombok.Getter;
import ru.pashabezborod.vigenereCoderServer.model.User.User;
import ru.pashabezborod.vigenereCoderServer.util.VigenereCoder;

@Getter
public class PasswordRs {

    private final String name, password;

    public PasswordRs(Password password, User user) {
        this.name = password.getName();
        this.password = VigenereCoder.decrypt(password.getPassword(), user);
    }

    public PasswordRs(String name) {
        this.name = name;
        this.password = null;
    }
}

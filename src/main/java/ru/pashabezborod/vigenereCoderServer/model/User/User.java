package ru.pashabezborod.vigenereCoderServer.model.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pashabezborod.vigenereCoderServer.model.Cookie.Cookie;

@Data
@Entity
@Builder
@Table(name = "vigenere_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Long password;

    public static User get(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .password(getPassword(userRequest.getPassword()))
                .build();
    }

    public static User get(UserRequest userRequest, int id) {
        return User.builder()
                .id(id)
                .name(userRequest.getName())
                .password(getPassword(userRequest.getPassword()))
                .build();
    }

    public static User get(String name, String password, int id) {
        return User.builder()
                .id(id)
                .name(name)
                .password(getPassword(password))
                .build();
    }

    private static Long getPassword(String password) {
        return password.repeat(12).hashCode() * 921L;
    }
}

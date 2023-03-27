package ru.pashabezborod.vigenereCoderServer.model.Password;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "password")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PasswordKey.class)
public class Password {
    @Id
    private Integer userId;
    @Id
    private String name;
    private String password;
    private String hash;
}

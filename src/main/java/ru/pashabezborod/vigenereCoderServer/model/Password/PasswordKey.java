package ru.pashabezborod.vigenereCoderServer.model.Password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordKey implements Serializable {
    private Integer userId;
    private String name;
}

package ru.pashabezborod.vigenereCoderServer.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.Field;
import ru.pashabezborod.vigenereCoderServer.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String password;

    public Map<Field, String> checkFields() {
        Map<Field, String> result = new HashMap<>();
        for (String error : ObjectUtils.checkName(name)) {
            result.put(Field.USER_NAME, error);
        }
        for (String error : ObjectUtils.checkPassword(password)) {
            result.put(Field.PASSWORD, error);
        }
        return result;
    }
}

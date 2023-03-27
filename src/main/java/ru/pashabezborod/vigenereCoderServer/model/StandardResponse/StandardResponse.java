package ru.pashabezborod.vigenereCoderServer.model.StandardResponse;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pashabezborod.vigenereCoderServer.model.Password.PasswordRs;

import java.util.List;

@Data
@NoArgsConstructor
public class StandardResponse {
    private ResponseStatus status;
    private String response;
    private List<PasswordRs> passwords;

    public StandardResponse (ResponseStatus status) {
        this.status = status;
    }

    public StandardResponse (ResponseStatus status, String response) {
        this.status = status;
        this.response = response;
    }

    public StandardResponse(ResponseStatus status, List<PasswordRs> passwords) {
        this.status = status;
        this.passwords = passwords;
    }
}

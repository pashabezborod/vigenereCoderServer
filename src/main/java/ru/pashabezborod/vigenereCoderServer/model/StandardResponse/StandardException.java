package ru.pashabezborod.vigenereCoderServer.model.StandardResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@org.springframework.web.bind.annotation.ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StandardException extends RuntimeException {

    private final ErrorPayload errorPayload;

    private StandardException (ErrorPayload errorPayload) {
        this.errorPayload = errorPayload;
    }

    @SneakyThrows
    public static RuntimeException get(Map<Field, String> failedFields, ResponseStatus status) {
        StandardException exception = new StandardException(new ErrorPayload(failedFields, status));
        return new RuntimeException(new ObjectMapper().writeValueAsString(exception.getErrorPayload()));
    }

    @SneakyThrows
    public static RuntimeException get(ResponseStatus status) {
        StandardException exception = new StandardException(new ErrorPayload(null, status));
        return new RuntimeException(new ObjectMapper().writeValueAsString(exception.errorPayload));
    }

    @Data
    @AllArgsConstructor
    private static class ErrorPayload {
        private Map<Field, String> failedFields;
        private ResponseStatus status;
    }
}

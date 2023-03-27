package ru.pashabezborod.vigenereCoderServer.model.StandardResponse;

public enum ResponseStatus {
    OK,
    BAD_FIELDS,
    BAD_COOKIE,
    USER_NOT_EXISTS,
    USER_ALREADY_EXISTS,
    PASSWORD_ALREADY_EXISTS,
    PASSWORD_NOT_EXISTS
}

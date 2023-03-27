package ru.pashabezborod.vigenereCoderServer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardException;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.Field;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.ResponseStatus;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardResponse;
import ru.pashabezborod.vigenereCoderServer.model.User.User;
import ru.pashabezborod.vigenereCoderServer.model.User.UserRequest;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CookieService cookieService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger("AuthServiceLogger");

    public StandardResponse login(UserRequest userRequest, String address) {
        Map<Field, String> failedFields = userRequest.checkFields();
        if (!failedFields.isEmpty()) throw StandardException.get(failedFields, ResponseStatus.BAD_FIELDS);
        Optional<User> user = userService.getUser(User.get(userRequest));
        if (user.isPresent()) {
            logger.info("User " + user.get().getName() + " logged in.");
            return new StandardResponse(ResponseStatus.OK, cookieService.getCookie(user.get(), address));
        } else throw StandardException.get(ResponseStatus.USER_NOT_EXISTS);
    }

    public StandardResponse logout(String cookie, String address) {
        if (cookie == null || cookie.isEmpty()) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        cookieService.deleteCookie(cookie, address);
        return new StandardResponse(ResponseStatus.OK);
    }
}
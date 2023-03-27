package ru.pashabezborod.vigenereCoderServer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.pashabezborod.vigenereCoderServer.model.Password.Password;
import ru.pashabezborod.vigenereCoderServer.model.Password.PasswordKey;
import ru.pashabezborod.vigenereCoderServer.model.Password.PasswordRq;
import ru.pashabezborod.vigenereCoderServer.model.Password.PasswordRs;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.Field;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.ResponseStatus;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardException;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardResponse;
import ru.pashabezborod.vigenereCoderServer.model.User.User;
import ru.pashabezborod.vigenereCoderServer.repository.PasswordRepository;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PasswordService {
    private final CookieService cookieService;
    private final UserService userService;
    private final PasswordRepository passwordRepository;
    private final Logger logger = LoggerFactory.getLogger("PasswordServiceLogger");

    @Transactional
    public StandardResponse savePassword(PasswordRq rq, String address) {
        Map<Field, String> failedFields = rq.checkFields();
        if (!failedFields.isEmpty())
            throw StandardException.get(failedFields, ResponseStatus.BAD_FIELDS);
        User user = cookieService.getUserByCookie(rq.getCookie(), address, userService);
        if (user == null) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        if (passwordRepository.findById(rq.getKey(user.getId())).isPresent())
            throw StandardException.get(ResponseStatus.PASSWORD_ALREADY_EXISTS);
        passwordRepository.save(rq.getPassword(user));
        logger.info("User {} saved password for {}", user.getName(), rq.getName());
        return new StandardResponse(ResponseStatus.OK);
    }

    @Transactional
    public StandardResponse deletePassword(PasswordRq rq, String address) {
        User user = cookieService.getUserByCookie(rq.getCookie(), address, userService);
        if (user == null) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        if (passwordRepository.findById(rq.getKey(user.getId())).isEmpty())
            throw StandardException.get(ResponseStatus.PASSWORD_NOT_EXISTS);
        passwordRepository.deleteById(new PasswordKey(user.getId(), rq.getName()));
        logger.info("User {} deleted password for {}", user.getName(), rq.getName());
        return new StandardResponse(ResponseStatus.OK);
    }

    @Transactional
    public StandardResponse editPassword (PasswordRq rq, String address) {
        User user = cookieService.getUserByCookie(rq.getCookie(), address, userService);
        if (user == null) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        Optional<Password> oldPassword = passwordRepository.findById(rq.getKey(user.getId()));
        if (oldPassword.isEmpty()) throw StandardException.get(ResponseStatus.PASSWORD_NOT_EXISTS);
        Map<Field, String> failedFields = rq.checkFields();
        if (!failedFields.isEmpty()) throw StandardException.get(failedFields, ResponseStatus.BAD_FIELDS);
        passwordRepository.delete(oldPassword.get());
        passwordRepository.save(rq.getPassword(user));
        logger.info("User {} changed password for {}", user.getName(), rq.getName());
        return new StandardResponse(ResponseStatus.OK);
    }

    public StandardResponse getPassword(String cookie, String passwordName, String address) {
        User user = cookieService.getUserByCookie(cookie, address, userService);
        if (user == null) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        Optional<Password> password = passwordRepository.findById(new PasswordKey(user.getId(), passwordName));
        return password.map(value -> new StandardResponse(ResponseStatus.OK, Collections.singletonList(new PasswordRs(value, user))))
                .orElseThrow(() -> StandardException.get(ResponseStatus.PASSWORD_NOT_EXISTS));
    }

    public StandardResponse getAllPasswords(String cookie, String address) {
        User user = cookieService.getUserByCookie(cookie, address, userService);
        if (user == null) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        List<String> names = passwordRepository.getAllByUserId(user.getId()).stream()
                .map(Password::getName)
                .toList();
        List<PasswordRs> response = new ArrayList<>();
        names.forEach(value -> response.add(new PasswordRs(value)));
        return new StandardResponse(ResponseStatus.OK, response);
    }
}

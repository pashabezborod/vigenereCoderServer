package ru.pashabezborod.vigenereCoderServer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.Field;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.ResponseStatus;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardException;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardResponse;
import ru.pashabezborod.vigenereCoderServer.model.User.User;
import ru.pashabezborod.vigenereCoderServer.model.User.UserRequest;
import ru.pashabezborod.vigenereCoderServer.repository.PasswordRepository;
import ru.pashabezborod.vigenereCoderServer.repository.UserRepository;
import ru.pashabezborod.vigenereCoderServer.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final CookieService cookieService;
    private final PasswordRepository passwordRepository;
    private final Logger logger = LoggerFactory.getLogger("UserServiceLogger");

    @Transactional
    public StandardResponse addUser(UserRequest userRequest) {
        Map<Field, String> failedFields = userRequest.checkFields();
        if (!failedFields.isEmpty()) throw StandardException.get(failedFields, ResponseStatus.BAD_FIELDS);
        if (repository.existsByName(userRequest.getName())) throw StandardException.get(ResponseStatus.USER_ALREADY_EXISTS);
        repository.save(User.get(userRequest));
        logger.info("New user added. " + userRequest.getName());
        return new StandardResponse(ResponseStatus.OK);
    }

    @Transactional
    public StandardResponse editUser(String cookie, String password, String address) {
        Map<Field, String> failedFields = new HashMap<>();
        for (String error : ObjectUtils.checkPassword(password))
            failedFields.put(Field.PASSWORD, error);
        if (!failedFields.isEmpty()) throw StandardException.get(failedFields, ResponseStatus.BAD_FIELDS);
        User user = cookieService.getUserByCookie(cookie, address, this);
        if (user == null) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        repository.save(User.get(user.getName(), password, user.getId()));
        logger.info("User password changed. " + user.getName());
        return new StandardResponse(ResponseStatus.OK);
    }

    @Transactional
    public StandardResponse deleteUser(String cookie, String address) {
        User user = cookieService.getUserByCookie(cookie, address, this);
        if (user == null) throw StandardException.get(ResponseStatus.BAD_COOKIE);
        cookieService.deleteUserCookie(user);
        //TODO: DELETE ALL PASSWORDS
        passwordRepository.deleteAllByUserId(user.getId());
        repository.delete(user);
        logger.info("User deleted. " + user.getName());
        return new StandardResponse(ResponseStatus.OK);
    }

    public Optional<User> getUser(User user) {
        return repository.findByNameAndPassword(user.getName(), user.getPassword());
    }

    public User getUser(int id) {
        return repository.getReferenceById(id);
    }
}

package ru.pashabezborod.vigenereCoderServer.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pashabezborod.vigenereCoderServer.model.Cookie.Cookie;
import ru.pashabezborod.vigenereCoderServer.model.User.User;
import ru.pashabezborod.vigenereCoderServer.repository.CookieRepository;

import java.util.Date;
import java.util.Optional;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CookieService {
    private final CookieRepository repository;
    @Value("${info.cookie_lifetime}")
    private int cookieLifetime;

    @Transactional
    public String getCookie(User user, String address) {
        repository.deleteAllByUser(user.getId());
        Cookie cookie = Cookie.builder()
                .user(user.getId())
                .address(address)
                .startsAt(new Date())
                .cookie(generateCookie())
                .build();
        return repository.save(cookie).getCookie();
    }

    public User getUserByCookie(String cookie, String address, UserService userService) {
        Optional<Cookie> cookieOptional = getCookie(cookie, address);
        if (cookieOptional.isPresent() && new Date().getTime() < cookieOptional.get().getStartsAt().getTime() + cookieLifetime)
            return userService.getUser(cookieOptional.get().getUser());
        else return null;
    }

    @Transactional
    public void deleteUserCookie(User user) {
        repository.deleteAllByUser(user.getId());
    }

    @Transactional
    public void deleteCookie(String cookie, String address) {
        repository.deleteAllByCookieAndAddress(cookie, address);
    }

    private Optional<Cookie> getCookie(String cookie, String address) {
        return repository.findByCookieAndAddress(cookie, address);
    }

    private String generateCookie() {
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build()
                .generate(50);
    }
}

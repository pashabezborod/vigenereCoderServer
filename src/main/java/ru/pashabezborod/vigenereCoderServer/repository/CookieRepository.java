package ru.pashabezborod.vigenereCoderServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import ru.pashabezborod.vigenereCoderServer.model.Cookie.Cookie;

import java.util.Optional;

@Repository
public interface CookieRepository extends JpaRepository<Cookie, Long> {

    void deleteAllByUser(int user);
    void deleteAllByCookieAndAddress(String cookie, String address);
    Optional<Cookie> findByCookieAndAddress(String cookie, String address);
}

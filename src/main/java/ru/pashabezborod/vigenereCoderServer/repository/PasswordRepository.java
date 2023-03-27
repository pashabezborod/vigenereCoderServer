package ru.pashabezborod.vigenereCoderServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pashabezborod.vigenereCoderServer.model.Password.Password;
import ru.pashabezborod.vigenereCoderServer.model.Password.PasswordKey;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password, PasswordKey> {

    List<Password> getAllByUserId(Integer userId);

    void deleteAllByUserId(Integer userId);
}

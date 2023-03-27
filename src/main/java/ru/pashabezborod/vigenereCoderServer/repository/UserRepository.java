package ru.pashabezborod.vigenereCoderServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pashabezborod.vigenereCoderServer.model.User.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNameAndPassword(String name, Long password);
    boolean existsByName(String name);
    int countByName(String name);
}

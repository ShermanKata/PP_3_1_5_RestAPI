package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);

    @Query("select user from User user join fetch user.roles where user.email = :email ")
    User findUserByEmail (String email);
}

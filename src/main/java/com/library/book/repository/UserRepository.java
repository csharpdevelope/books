package com.library.book.repository;

import com.library.book.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    @Query(value = "select count(u) from User u")
    long countAllUsers();

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByToken(String token);
}

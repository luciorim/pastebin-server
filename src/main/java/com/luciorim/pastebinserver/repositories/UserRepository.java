package com.luciorim.pastebinserver.repositories;

import com.luciorim.pastebinserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

}



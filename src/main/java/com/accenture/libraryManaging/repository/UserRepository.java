package com.accenture.libraryManaging.repository;

import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User save(User user);
    User findByUsername(String username);

    Optional<User> findById(Long id);
}

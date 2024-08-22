package com.accenture.library.repository;

import com.accenture.library.repository.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    List<UserBook> findByUserId(Long userId);

    List<UserBook> findByBookId(Long bookId);

    UserBook findByUserIdAndBookId(Long userId, Long bookId);

}
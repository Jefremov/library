package com.accenture.libraryManaging.repository;

import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    List<UserBook> findByUserId(Long userId);

    List<UserBook> findByBookId(Long bookId);

}
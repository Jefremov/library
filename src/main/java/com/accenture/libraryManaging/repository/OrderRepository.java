package com.accenture.libraryManaging.repository;

import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    void deleteByBookId(Long bookId);

    List<Order> findByBookId(Long bookIsbn);

    boolean existsByBookIdAndUserId(Long bookId, Long userId);
}

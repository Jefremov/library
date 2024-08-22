package com.accenture.library.repository;

import com.accenture.library.repository.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    void deleteByBookId(Long bookId);

    List<Order> findByBookId(Long bookIsbn);

    boolean existsByBookIdAndUserId(Long bookId, Long userId);
}

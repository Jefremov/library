package com.accenture.libraryManaging.repository;

import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


}

package com.accenture.libraryManaging.repository;

import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Book findByIsbn(String isbn);

    Book findByTitle(String title);

    List<Book> findByAuthor(String author);

    Book save(Book book);

    Optional<Book> findById(Long id);
}
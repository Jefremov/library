package com.accenture.library.factory.impl;

import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.factory.*;

import com.accenture.library.repository.entity.*;
import org.springframework.stereotype.*;

import static com.accenture.library.repository.entity.Genre.*;


@Component
public class BookFactoryImpl implements BookFactory {

    @Override
    public Book createBook(BookDto bookDto) throws IllegalGenreException {

        Book book;

        switch (bookDto.getGenre().toUpperCase()) {
            case "CHILDREN":
                book = new ChildrenBook();
                book.setGenre(CHILDREN);
                break;
            case "FICTION":
                book = new FictionBook();
                book.setGenre(FICTION);
                break;
            case "HISTORICAL":
                book = new HistoricalBook();
                book.setGenre(HISTORICAL);
                break;
            case "SCIENTIFIC":
                book = new ScientificBook();
                book.setGenre(SCIENTIFIC);
                break;
            default:
                throw new IllegalGenreException("Invalid genre");
        }
        return book;
    }

}

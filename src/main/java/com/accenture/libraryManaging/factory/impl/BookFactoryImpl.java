package com.accenture.libraryManaging.factory.impl;

import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.factory.*;
import com.accenture.libraryManaging.mapper.*;

import com.accenture.libraryManaging.repository.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import static com.accenture.libraryManaging.repository.entity.Genre.*;


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

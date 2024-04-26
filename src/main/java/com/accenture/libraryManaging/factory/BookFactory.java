package com.accenture.libraryManaging.factory;

import com.accenture.libraryManaging.dto.*;
import com.accenture.libraryManaging.exceptions.*;
import com.accenture.libraryManaging.repository.entity.*;

import java.util.*;

public interface BookFactory {

    Book createBook(BookDto bookDto) throws IllegalGenreException;


}

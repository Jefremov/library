package com.accenture.library.factory;

import com.accenture.library.dto.*;
import com.accenture.library.exceptions.*;
import com.accenture.library.repository.entity.*;

public interface BookFactory {

    Book createBook(BookDto bookDto) throws IllegalGenreException;


}

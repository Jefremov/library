package com.accenture.libraryManaging;

import com.accenture.libraryManaging.Randomizer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LibraryManagingApplication {

	private static final String LINE_SEPARATOR = "-----------------------------------------------";

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagingApplication.class, args);

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Adding books ------------");
		System.out.println(LINE_SEPARATOR);
		AddingBooksRandomizer.addRandomBooks();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Adding users ------------");
		System.out.println(LINE_SEPARATOR);
		AddingUserRandomizer.addUsers();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Taking books ------------");
		System.out.println(LINE_SEPARATOR);
		LibraryRandomizer.takeRandomBooks();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Ordering books ------------");
		System.out.println(LINE_SEPARATOR);
		LibraryRandomizer.orderRandomBooks();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Books return ------------");
		System.out.println(LINE_SEPARATOR);
		LibraryRandomizer.returnRandomBooks();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Get users info ------------");
		System.out.println(LINE_SEPARATOR);
		GetInfoRandomizer.getUsersInfo();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Get book info by ISBN ------------");
		System.out.println(LINE_SEPARATOR);
		GetInfoRandomizer.getBooksInfoByIsbn();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Get book info by author ------------");
		System.out.println(LINE_SEPARATOR);
		GetInfoRandomizer.getBooksInfoByAuthor();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Get book info by title ------------");
		System.out.println(LINE_SEPARATOR);
		GetInfoRandomizer.getBooksInfoByTitle();

		System.out.println(LINE_SEPARATOR);
		System.out.println("--------- Get all books info ------------");
		System.out.println(LINE_SEPARATOR);
		GetInfoRandomizer.getAllBooksInfo();

	}

}

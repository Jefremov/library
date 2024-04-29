# Assignment

Develop a program for managing a library using the Java programming language. The program must satisfy the following conditions:

## Creational GoF Patterns
- Utilize the Factory Method pattern to create instances of books of different genres.

## Structural GoF Patterns
- Utilize the Decorator pattern is applied to extend the functionality of books. The description automatically includes a list of users who have borrowed this book.

## Behavioral GoF Patterns
- Use the Observer pattern to implement a mechanism for notifying users about the appearance of ordered books in the library.

## Other GoF Patterns
- Singleton pattern for connecting to the mail server to send notifications to users.
- Iterator pattern for traversing the collection of books.
- Template Method pattern for taking and returning a book

## Brief Description

The program utilizes:

- Unit test coverage: 84%. Due to the requirement for automatic execution of the application, changes had to be made to the application. Some tests do not execute
- [Documentation](http://localhost:8080/swagger-ui/index.html#/)
- Database: H2.

Application functionality:

- Adding a new user, where the name must be unique.
- Adding a new book, where the ISBN field must be unique. When adding a book with an existing ISBN, the availability counter increases. There are no checks for uniqueness of author, title, publishing year, genre, or description.
- User borrowing a book. Upon borrowing, user information is added to the description field.
- User returning a book.
- If the book is not available, the user has the opportunity to order the book. After returning this book to the library, users who ordered the book will receive email about the availability of the book
- Viewing user information by name. User information includes all books they have borrowed
- Viewing book information by ISBN.
- Viewing book information by author.
- Viewing book information by title.
- Viewing information about all books. 

Book information includes all users currently reading that book.
When taking a book, checks are performed for: the validity of the user, the book, and re-taking the book.
When returning a book, checks are performed for: the validity of the user, the book, whether the user has the book.
When ordering a book when ordering a book: validity of the user, book, availability of the book from the user, ordering the book again.

## Automated test scenario:
- Up to 20 users are created with any name from 12 options.
- Up to 20 books are created with any of the 12 ISBN options and any of the 6 author options.
- Random users with any name from 20 options borrow 50 random books with any of the 20 ISBN options.
- Random users with any name from 20 options order 50 random books with any of the 20 ISBN options.
- Random users with any name from 20 options return 50 random books with any of the 20 ISBN options.
- View information about 20 users with any name from 20 options.
- View information about 20 books with any ISBN from 20 options.
- View information about 20 books with any author from 20 options.
- View information about 20 books with any title from 20 options.
- View information about all books.
package com.belhard.bookstore.data.conversion;

import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.entity.Book;

public class BookDataConversion {
    public static BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();

        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setYear(book.getYear());
        bookDto.setCost(book.getCost());
        bookDto.setCoverType(book.getCoverType());
        return bookDto;
    }

    public static Book toBook(BookDto bookDto) {
        Book book = new Book();

        book.setId(bookDto.getId());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setYear(bookDto.getYear());
        book.setCost(bookDto.getCost());
        book.setCoverType(bookDto.getCoverType());

        return book;
    }

}

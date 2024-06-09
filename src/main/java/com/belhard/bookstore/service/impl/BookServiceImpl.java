package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.controller.NotFoundException;
import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.CoverType;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.UserDtoWithoutPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();

        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setYear(book.getYear());
        bookDto.setCost(book.getCost());
        bookDto.setCoverType(book.getCoverType());
        return bookDto;
    }

    private Book toBook(BookDto bookDto) {
        Book book = new Book();

        book.setId(bookDto.getId());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setYear(bookDto.getYear());
        book.setCost(bookDto.getCost());
        book.setCoverType(bookDto.getCoverType());

        return book;
    }

    @Override
    public BookDto getById(long id) {
        log.debug("Calling getById");

        Book book = bookRepository.findById(id);

        if (book == null) {
            throw new NotFoundException("Book whith id = " + id + " is not found!");
        } else {
            return toDto(book);
        }
    }


    @Override
    public BookDto getByIsbn(String isbn) {
        log.debug("Calling getByIsbn");

        Book book= bookRepository.findByIsbn(isbn);

        if (book == null){
            throw new NotFoundException("Book whith isbn = " + isbn + " is not found!");
        } else {
            return toDto(book);
        }
    }

    @Override
    public List<BookDto> getAll() {
        log.debug("Calling getAll");

       List<Book> listBook =  bookRepository.findAll();

        if (listBook.isEmpty()){
            throw new NotFoundException("Books are not found!");
        } else {
            return listBook
                    .stream()
                    .map(this::toDto)
                    .toList();
        }
    }

    @Override
    public List<BookDto> getByAuthor(String author) {
        log.debug("Calling getByAuthor");

        List<Book> listBook =  bookRepository.findByAuthor(author);

        if (listBook.isEmpty()){
            throw new NotFoundException("Books are not found!");
        } else {
            return listBook
                    .stream()
                    .map(this::toDto)
                    .toList();
        }
    }

    @Override
    public BookDto create(BookDto bookDto) {

        bookDto.setCoverType(CoverType.valueOf("HARD"));
        log.debug("Calling create");
        String isbnToBeCreate = bookDto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(isbnToBeCreate);

        if (byIsbn != null) {
            throw new NotFoundException("No valid isbn" + bookDto.getIsbn() + "! Book is not created!");
        }

        Book book = bookRepository.save(toBook(bookDto));

        if (book == null){
            throw new NotFoundException("Book is not create!");
        } else {
            return toDto(book);
        }
    }

    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("Calling update");

        String isbnToBeUpdated = bookDto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(isbnToBeUpdated);

        if (byIsbn != null && !byIsbn.getId().equals(bookDto.getId())) {
            throw new NotFoundException("No valid isbn" + bookDto.getIsbn() + "! Book  is not updated!");
        }

        if (bookDto.getAuthor() == null) {
            bookDto.setAuthor(byIsbn.getAuthor());
        }
        if (bookDto.getYear() == null) {
            bookDto.setYear(byIsbn.getYear());
        }

        if (bookDto.getCost() == null) {
            bookDto.setCost(byIsbn.getCost());
        }

        if (bookDto.getCoverType() == null) {
            bookDto.setCoverType(byIsbn.getCoverType());
        }

        Book book = bookRepository.save(toBook(bookDto));
        if (book == null){
            throw new NotFoundException("Book is not update!");
        } else {
            return toDto(book);
        }
    }

    @Override
    public void delete(long id) {
        log.debug("Calling delete");

        if (!bookRepository.delete(id)) {
            throw new NotFoundException("Deletion error by id = " + id + "!" );
        }
    }

    @Override
    public long getCountAll() {
        log.debug("Calling getCountAll");
        return bookRepository.countAll();
    }

    @Override
    public BigDecimal getCostByAuthor(String author) {
        log.debug("Calling getCostByAuthor");
        List<Book> bookList = bookRepository.findByAuthor(author);

        if (bookList.isEmpty()){
            throw new NotFoundException("Books by author " + author + " are not found!" );
        } else {
            BigDecimal i = BigDecimal.valueOf(0);
            for (Book book : bookList) {
                if (book.getCost() != null) {
                    i = i.add(book.getCost());
                }
            }
            return i;
        }
    }
}

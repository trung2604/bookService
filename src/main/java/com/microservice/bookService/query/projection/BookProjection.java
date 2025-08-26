package com.microservice.bookService.query.projection;

import com.microservice.bookService.command.data.Book;
import com.microservice.bookService.command.data.BookRepository;
import com.microservice.bookService.query.model.BookResponse;
import com.microservice.bookService.query.queries.GetAllBookQuery;
import com.microservice.bookService.query.queries.GetBookByIdQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponse> handle(GetAllBookQuery query) {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> bookResponses = new ArrayList<>();
        books.forEach(book -> {
            BookResponse bookResponse = new BookResponse();
            BeanUtils.copyProperties(book, bookResponse);
            bookResponses.add(bookResponse);
        });
        return bookResponses;
    }

    @QueryHandler
    public BookResponse handle(GetBookByIdQuery query) {
        Book book = bookRepository.findById(query.getId()).orElse(null);
        BookResponse bookResponse = new BookResponse();
        if (book != null) {
            BeanUtils.copyProperties(book, bookResponse);
        }
        return bookResponse;
    }
}

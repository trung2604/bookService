package com.microservice.bookService.query.controller;

import com.microservice.bookService.query.model.BookResponse;
import com.microservice.bookService.query.queries.GetAllBookQuery;
import com.microservice.bookService.query.queries.GetBookByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BookResponse> getAllBooks() {
        GetAllBookQuery getAllBookQuery = new GetAllBookQuery();
        return queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponse.class)).join();
    }

    @GetMapping("/{bookId}")
    public BookResponse getBookById(@PathVariable String bookId) {
        GetBookByIdQuery getBookByIdQuery = new GetBookByIdQuery(bookId);
        return queryGateway.query(getBookByIdQuery, ResponseTypes.instanceOf(BookResponse.class)).join();
    }
}

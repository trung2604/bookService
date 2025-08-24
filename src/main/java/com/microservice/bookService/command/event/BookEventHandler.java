package com.microservice.bookService.command.event;

import com.microservice.bookService.command.data.BookRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.microservice.bookService.command.data.Book;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookEventHandler {
    @Autowired
    private BookRepository bookRepository;
    
    @EventHandler
    public void on(BookCreateEvent event) {
        log.info("Received BookCreateEvent: {}", event);
        
        try {
            if (bookRepository.existsById(event.getId())) {
                log.warn("Book with ID {} already exists, skipping event", event.getId());
                return;
            }
            
            Book book = new Book();
            book.setId(event.getId());
            book.setName(event.getName());
            book.setAuthor(event.getAuthor());
            book.setIsReady(event.getIsReady());
            log.info("Saving book to database: {}", book);
            Book savedBook = bookRepository.save(book);
            log.info("Book saved successfully with ID: {}", savedBook.getId());
            
        } catch (Exception e) {
            log.error("Error saving book to database: {}", e.getMessage(), e);
            throw e;
        }
    }
}

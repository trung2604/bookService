package com.microservice.bookService.command.controller;

import com.microservice.bookService.command.commands.CreateBookCommand;
import com.microservice.bookService.command.model.BookRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public String createBook(@RequestBody BookRequest bookRequest) {
        String bookName = bookRequest.getName();
        String bookAuthor = bookRequest.getAuthor();
        CreateBookCommand createBookCommand = new CreateBookCommand(UUID.randomUUID().toString(), bookName, bookAuthor, true);
        return commandGateway.sendAndWait(createBookCommand);
    }
}

package com.microservice.bookService.command.controller;

import com.microservice.bookService.command.model.ApiResponse;
import com.microservice.bookService.command.commands.CreateBookCommand;
import com.microservice.bookService.command.commands.DeleteBookCommand;
import com.microservice.bookService.command.commands.UpdateBookCommand;
import com.microservice.bookService.command.model.BookRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createBook(@RequestBody BookRequest bookRequest) {
        try {
            String bookName = bookRequest.getName();
            String bookAuthor = bookRequest.getAuthor();
            CreateBookCommand createBookCommand = new CreateBookCommand(UUID.randomUUID().toString(), bookName, bookAuthor, true);
            String result = commandGateway.sendAndWait(createBookCommand);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Create book successfully", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create book: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<ApiResponse<String>> updateBook(@RequestBody BookRequest bookRequest, @PathVariable String bookId) {
        try {
            UpdateBookCommand updateBookCommand = new UpdateBookCommand(bookId, bookRequest.getName(), bookRequest.getAuthor(), bookRequest.getIsReady());
            String result = commandGateway.sendAndWait(updateBookCommand);
            return ResponseEntity.ok(ApiResponse.success("Update book successfully", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update book: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable String bookId) {
        try {
            DeleteBookCommand deleteBookCommand = new DeleteBookCommand(bookId);
            commandGateway.sendAndWait(deleteBookCommand);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse.success("Delete book successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete book: " + e.getMessage()));
        }
    }
}

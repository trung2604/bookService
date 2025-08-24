package com.microservice.bookService.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}

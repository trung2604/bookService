package com.microservice.bookService.command.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id; // Remove @GeneratedValue since we set ID manually

    private String name;

    private String author;

    private Boolean isReady;

    @Version
    private Long version = 0L;
}

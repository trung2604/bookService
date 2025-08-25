package com.microservice.bookService.command.agregate;

import com.microservice.bookService.command.commands.CreateBookCommand;
import com.microservice.bookService.command.commands.DeleteBookCommand;
import com.microservice.bookService.command.commands.UpdateBookCommand;
import com.microservice.bookService.command.event.BookCreateEvent;
import com.microservice.bookService.command.event.BookDeleteEvent;
import com.microservice.bookService.command.event.BookUpdateEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class BookAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand command) {
        BookCreateEvent event = new BookCreateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookCreateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @CommandHandler
    public void handleUpdate(UpdateBookCommand command) {
        BookUpdateEvent event = new BookUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @CommandHandler
    public void handleDelete (DeleteBookCommand command) {
        BookDeleteEvent event = new BookDeleteEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event) {
        this.id = event.getId();
        AggregateLifecycle.markDeleted();
    }
}

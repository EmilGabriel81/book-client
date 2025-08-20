package com.client.book_client.controller;

import com.client.book_client.service.ExternalBookService;
import com.client.book_client.model.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ExternalBookController {


    private final ExternalBookService externalBookService;

    @GetMapping("/books")
    public CompletableFuture<List<BookDTO>> getBooks() {
        return externalBookService.getBooksAsync();
    }
}
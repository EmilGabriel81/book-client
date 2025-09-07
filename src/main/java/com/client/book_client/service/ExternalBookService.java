package com.client.book_client.service;

import com.client.book_client.client.BookFeignClient;
import com.client.book_client.kafka.BookProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.common.dto.BookDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ExternalBookService {

    private final BookFeignClient bookFeignClient;
    private final BookProducer bookProducer;

    @Async
    public CompletableFuture<List<BookDTO>> getBooksAsync() {
        List<BookDTO> books = bookFeignClient.getBooks();

        books.forEach(bookProducer::sendBookMessage);

        return CompletableFuture.completedFuture(books);
    }
}

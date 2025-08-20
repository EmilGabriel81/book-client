package com.client.book_client.service;

import com.client.book_client.model.BookDTO;
import com.client.book_client.client.BookFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ExternalBookService {
    private final BookFeignClient bookFeignClient;



    @Async
    public CompletableFuture<List<BookDTO>> getBooksAsync() {
        List<BookDTO> books = bookFeignClient.getBooks();
        return CompletableFuture.completedFuture(books);
    }
}

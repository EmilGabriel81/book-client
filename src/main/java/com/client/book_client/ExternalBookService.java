package com.client.book_client;

import com.client.book_client.model.BookDTO;
import com.client.book_client.scrapper.BookClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalBookService {
    private final BookClient bookClient;

    public List<BookDTO> getBooksFromScrapper() {
        return bookClient.getBooks();
    }
}

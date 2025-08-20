package com.client.book_client.scrapper;

import com.client.book_client.model.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "scrapper", url = "http://localhost:8081/scrapper")
public interface BookClient {

    @GetMapping("/books")
    List<BookDTO> getBooks();
}

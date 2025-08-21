package com.client.book_client.client;

import com.client.book_client.config.FeignConfig;
import com.client.book_client.exception.fallback.BookFeignFallbackFactory;
import org.common.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "scrapper", url = "http://localhost:8080/scrapper",
        configuration = FeignConfig.class,
        fallbackFactory = BookFeignFallbackFactory.class)
public interface BookFeignClient {

    @GetMapping("/books")
    List<BookDTO> getBooks();
}

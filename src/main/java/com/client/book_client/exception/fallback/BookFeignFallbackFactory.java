package com.client.book_client.exception.fallback;

import java.net.ConnectException;
import com.client.book_client.exception.ScrapperServiceUnavailableException;
import com.client.book_client.client.BookFeignClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class BookFeignFallbackFactory implements FallbackFactory<BookFeignClient> {

    @Override
    public BookFeignClient create(Throwable cause) {
        return () -> {
            if (cause.getCause() instanceof ConnectException) {
                throw new ScrapperServiceUnavailableException(
                        "Scrapper service is offline or unreachable", cause
                );
            }
            throw new RuntimeException("Unexpected Feign error", cause);
        };
    }
}

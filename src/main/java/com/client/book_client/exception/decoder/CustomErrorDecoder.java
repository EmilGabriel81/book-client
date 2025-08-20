package com.client.book_client.exception.decoder;

import com.client.book_client.exception.ScrapperServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();


    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 503) {
            return new ScrapperServiceUnavailableException(
                    "Scrapper service returned 503 Service Unavailable", null
            );
        }
        if (response.status() == 500) {
            return new ScrapperServiceUnavailableException(
                    "Scrapper service failed with 500 Internal Server Error", null
            );
        }
        return defaultDecoder.decode(methodKey, response);
    }
}

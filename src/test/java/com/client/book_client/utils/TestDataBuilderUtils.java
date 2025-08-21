package com.client.book_client.utils;

import org.common.dto.BookDTO;

public final class TestDataBuilderUtils {

    public static BookDTO getBookDTO(String title, double price) {
        return BookDTO.builder().withTitle(title).withPrice(price).build();
    }
}

package com.client.book_client.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class BookDTO {
    private String title;
    private double price;
}

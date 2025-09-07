package com.client.book_client.kafka;

import lombok.RequiredArgsConstructor;
import org.common.dto.BookDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookProducer {

    private final KafkaTemplate<String, BookDTO> kafkaTemplate;

    @Value("${app.kafka.books-topic}")
    private String booksTopic;

    public void sendBookMessage(BookDTO book) {
        kafkaTemplate.send(booksTopic, book);
    }
}
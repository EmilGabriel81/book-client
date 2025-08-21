package com.client.book_client.controller;

import com.client.book_client.exception.ScrapperServiceUnavailableException;
import com.client.book_client.service.ExternalBookService;
import com.client.book_client.utils.TestDataBuilderUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.MatcherAssert.assertThat;

import org.common.dto.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ExternalBookController.class)
class BookControllerFuncTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExternalBookService externalBookService;

    @Autowired
    private ObjectMapper objectMapper;

    private final BookDTO testBookDTO1 = TestDataBuilderUtils.getBookDTO("A Light in the ...", 51.77);
    private final BookDTO testBookDTO2 = TestDataBuilderUtils.getBookDTO("Tipping the Velvet", 53.74);

    @Test
    void shouldReturnFirstTwoBooks() throws Exception {
        List<BookDTO> mockBooks = List.of(
                testBookDTO1,
                testBookDTO2);
        given(externalBookService.getBooksAsync())
                .willReturn(CompletableFuture.completedFuture(mockBooks));

        final MvcResult result = mockMvc.perform(get("/client/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        System.out.println("Response JSON: " + result.getResponse().getContentAsString() + "...");

        List<BookDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.size(), greaterThanOrEqualTo(2));
    }

    @Test
    void shouldReturnBooks() throws Exception {
        // given
        List<BookDTO> mockBooks = List.of(
                new BookDTO("A Light in the ...", 51.77),
                new BookDTO("Tipping the Velvet", 53.74));
        given(externalBookService.getBooksAsync())
                .willReturn(CompletableFuture.completedFuture(mockBooks));

        // when + then
        mockMvc.perform(get("/client/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A Light in the ..."))
                .andExpect(jsonPath("$[0].price").value(51.77))
                .andExpect(jsonPath("$[1].title").value("Tipping the Velvet"))
                .andExpect(jsonPath("$[1].price").value(53.74));
        ;
    }

    @Test
    void shouldHandleServiceUnavailable() throws Exception {

        given(externalBookService.getBooksAsync())
                .willThrow(new ScrapperServiceUnavailableException("Scrapper down", null));

        mockMvc.perform(get("/client/books"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value(503))
                .andExpect(jsonPath("$.error").value("Service Unavailable"))
                .andExpect(jsonPath("$.message").value("Scrapper down"))
                .andExpect(jsonPath("$.path").value("/client/books"));
    }
}

package com.example.samplejava2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createBook() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\",\"year\":2008}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.year").value(2008));
    }

    @Test
    void listBooks() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Effective Java\",\"author\":\"Joshua Bloch\",\"year\":2018}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookNotFound() throws Exception {
        mockMvc.perform(get("/books/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not found"));
    }

    @Test
    void updateBook() throws Exception {
        String result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Old Title\",\"author\":\"Author\",\"year\":2020}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id = com.jayway.jsonpath.JsonPath.read(result, "$.id").toString();

        mockMvc.perform(put("/books/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Title\",\"author\":\"Author\",\"year\":2021}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.year").value(2021));
    }

    @Test
    void deleteBook() throws Exception {
        String result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"To Delete\",\"author\":\"Author\",\"year\":2020}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id = com.jayway.jsonpath.JsonPath.read(result, "$.id").toString();

        mockMvc.perform(delete("/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("deleted"));
    }

    @Test
    void searchBooks() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Spring in Action\",\"author\":\"Craig Walls\",\"year\":2022}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/books/search").param("q", "spring"))
                .andExpect(status().isOk());
    }

    @Test
    void createBookValidationBlankTitle() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"author\":\"Author\",\"year\":2020}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBookValidationInvalidYear() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book\",\"author\":\"Author\",\"year\":999}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBookValidationMissingAuthor() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book\",\"year\":2020}"))
                .andExpect(status().isBadRequest());
    }
}

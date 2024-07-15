package halatsiankova.javafromscratch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketControllerTest {
    @Autowired
    private MockMvc mvc;

    @WithMockUser("spring")
    @Test
    void shouldAuthRequestSuccessfullyWith200() throws Exception {
        var responseBody = mvc.perform(get("/ticket/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        assertEquals("{\"ticketType\":\"DAY\",\"ticketDate\":[2024,6,30,10,0],\"userId\":1}", responseBody);
    }

    @Test
    void shouldRequestWith401WhenUnauthorized() throws Exception {
        mvc.perform(get("/ticket/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
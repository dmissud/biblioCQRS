package com.bibliocqrs.command.infra.web;

import com.bibliocqrs.command.domain.AjouterExemplaireCommand;
import com.bibliocqrs.command.domain.OuvrageCommandHandler;
import com.bibliocqrs.command.domain.ReferencerOuvrageCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OuvrageCommandController.class)
@ContextConfiguration(classes = OuvrageCommandController.class)
public class OuvrageCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OuvrageCommandHandler commandHandler;

    @Test
    void referencerOuvrage_shouldReturn202Accepted() throws Exception {
        String payload = """
                {
                    "isbn": "978-1234567890",
                    "titre": "Clean Architecture",
                    "auteur": "Robert C. Martin"
                }
                """;

        mockMvc.perform(post("/api/ouvrages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isAccepted());

        verify(commandHandler).handle(argThat((ReferencerOuvrageCommand cmd) ->
                cmd.isbn().equals("978-1234567890") &&
                        cmd.titre().equals("Clean Architecture") &&
                        cmd.auteur().equals("Robert C. Martin")
        ));
    }

    @Test
    void ajouterExemplaire_shouldReturn202Accepted() throws Exception {
        String payload = """
                {
                    "salle": "Salle B",
                    "etagere": "Etagère 3",
                    "position": "Pos 1"
                }
                """;

        mockMvc.perform(post("/api/ouvrages/978-1234567890/exemplaires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isAccepted());

        verify(commandHandler).handle(argThat((AjouterExemplaireCommand cmd) ->
                cmd.isbnOuvrage().equals("978-1234567890") &&
                        cmd.salle().equals("Salle B") &&
                        cmd.etagere().equals("Etagère 3") &&
                        cmd.position().equals("Pos 1")
        ));
    }
}

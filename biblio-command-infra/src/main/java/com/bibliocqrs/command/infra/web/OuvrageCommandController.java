package com.bibliocqrs.command.infra.web;

import com.bibliocqrs.command.domain.AjouterExemplaireCommand;
import com.bibliocqrs.command.domain.OuvrageCommandHandler;
import com.bibliocqrs.command.domain.ReferencerOuvrageCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ouvrages")
public class OuvrageCommandController {

    private final OuvrageCommandHandler commandHandler;

    public OuvrageCommandController(OuvrageCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    public ResponseEntity<Void> referencerOuvrage(@RequestBody ReferencerOuvrageRequest request) {
        ReferencerOuvrageCommand command = new ReferencerOuvrageCommand(request.isbn(), request.titre(), request.auteur());
        commandHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{isbn}/exemplaires")
    public ResponseEntity<Void> ajouterExemplaire(@PathVariable("isbn") String isbn, @RequestBody AjouterExemplaireRequest request) {
        AjouterExemplaireCommand command = new AjouterExemplaireCommand(isbn, request.salle(), request.etagere(), request.position());
        commandHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}

package com.bibliocqrs.command.domain;

public record ReferencerOuvrageCommand(String isbn, String titre, String auteur) {
}

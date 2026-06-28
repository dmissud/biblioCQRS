package com.bibliocqrs.command.infra.web;

public record ReferencerOuvrageRequest(String isbn, String titre, String auteur) {
}

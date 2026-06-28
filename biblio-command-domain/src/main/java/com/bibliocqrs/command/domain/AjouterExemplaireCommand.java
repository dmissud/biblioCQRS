package com.bibliocqrs.command.domain;

public record AjouterExemplaireCommand(String isbnOuvrage, String salle, String etagere, String position) {
}

package com.bibliocqrs.command.infra.web;

public record AjouterExemplaireRequest(String codeBarre, String salle, String etagere, String position) {
}

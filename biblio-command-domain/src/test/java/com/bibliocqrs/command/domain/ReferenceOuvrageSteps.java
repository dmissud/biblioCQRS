package com.bibliocqrs.command.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceOuvrageSteps {

    private final Map<String, Ouvrage> inMemoryDb = new HashMap<>();
    
    private final OuvrageRepository repository = new OuvrageRepository() {
        @Override
        public void save(Ouvrage ouvrage) {
            inMemoryDb.put(ouvrage.getIsbn(), ouvrage);
        }

        @Override
        public Optional<Ouvrage> findByIsbn(String isbn) {
            return Optional.ofNullable(inMemoryDb.get(isbn));
        }
    };
    
    private final OuvrageCommandHandler handler = new OuvrageCommandHandler(repository);

    private Exception lastException;

    @Given("qu'aucun ouvrage avec l'ISBN {string} n'existe")
    public void qu_aucun_ouvrage_avec_l_isbn_n_existe(String isbn) {
        inMemoryDb.remove(isbn);
        lastException = null;
    }

    @Given("que l'ouvrage avec l'ISBN {string} existe déjà dans le catalogue")
    public void que_l_ouvrage_avec_l_isbn_existe_déjà_dans_le_catalogue(String isbn) {
        inMemoryDb.put(isbn, new Ouvrage(isbn, "Titre Existant", "Auteur Existant"));
        lastException = null;
    }

    @When("le bibliothécaire référence l'ouvrage avec l'ISBN {string}, le titre {string} et l'auteur {string}")
    @When("le bibliothécaire tente de référencer à nouveau l'ouvrage avec l'ISBN {string}, le titre {string} et l'auteur {string}")
    public void le_bibliothécaire_référence_l_ouvrage(String isbn, String titre, String auteur) {
        ReferencerOuvrageCommand command = new ReferencerOuvrageCommand(isbn, titre, auteur);
        try {
            handler.handle(command);
        } catch (Exception e) {
            this.lastException = e;
        }
    }

    @Then("l'ouvrage est ajouté au catalogue avec succès")
    public void l_ouvrage_est_ajouté_au_catalogue_avec_succès() {
        assertThat(lastException).isNull();
        assertThat(inMemoryDb.containsKey("978-2-07-036822-8")).isTrue();
        Ouvrage saved = inMemoryDb.get("978-2-07-036822-8");
        assertThat(saved.getTitre()).isEqualTo("1984");
        assertThat(saved.getAuteur()).isEqualTo("George Orwell");
        
        // Vérification du Domain Event
        var events = saved.pullDomainEvents();
        assertThat(events).hasSize(1);
        assertThat(events.get(0)).isInstanceOf(com.bibliocqrs.core.events.OuvrageReferenceEvent.class);
        com.bibliocqrs.core.events.OuvrageReferenceEvent event = (com.bibliocqrs.core.events.OuvrageReferenceEvent) events.get(0);
        assertThat(event.isbn()).isEqualTo("978-2-07-036822-8");
    }

    @Then("le référencement est refusé pour cause de doublon")
    public void le_référencement_est_refusé_pour_cause_de_doublon() {
        assertThat(lastException).isNotNull();
        assertThat(lastException).isInstanceOf(IllegalArgumentException.class);
        assertThat(lastException.getMessage()).isEqualTo("Un ouvrage avec cet ISBN existe déjà.");
    }

    @Given("que l'ouvrage {string} avec l'ISBN {string} existe dans le catalogue")
    public void que_l_ouvrage_avec_l_isbn_existe_dans_le_catalogue(String titre, String isbn) {
        Ouvrage o = new Ouvrage(isbn, titre, "Auteur inconnu");
        o.pullDomainEvents(); // On vide les événements de création initiale
        inMemoryDb.put(isbn, o);
    }

    @When("le bibliothécaire ajoute un exemplaire identifié par {string} en {string}, {string}, {string} à l'ouvrage {string}")
    public void le_bibliothécaire_ajoute_un_exemplaire_identifié_par_en_à_l_ouvrage(String codeBarre, String salle, String etagere, String position, String isbn) {
        AjouterExemplaireCommand command = new AjouterExemplaireCommand(isbn, codeBarre, salle, etagere, position);
        handler.handle(command);
    }

    @Then("un nouvel exemplaire identifié par {string} est enregistré pour cet ouvrage")
    public void un_nouvel_exemplaire_identifie_par_est_enregistre(String codeBarre) {
        Ouvrage ouvrage = inMemoryDb.get("978-2-07-036822-8");
        assertThat(ouvrage.getExemplaires()).hasSize(1);
        assertThat(ouvrage.getExemplaires().get(0).getCodeBarre()).isEqualTo(codeBarre);
    }

    @Then("il est assigné à la {string}, {string}, {string}")
    public void il_est_assigné_à_la(String salle, String etagere, String position) {
        Ouvrage ouvrage = inMemoryDb.get("978-2-07-036822-8");
        Exemplaire exemplaire = ouvrage.getExemplaires().get(0);
        assertThat(exemplaire.getLieuStockage().salle()).isEqualTo(salle);
        assertThat(exemplaire.getLieuStockage().etagere()).isEqualTo(etagere);
        assertThat(exemplaire.getLieuStockage().position()).isEqualTo(position);
        
        // Vérification du Domain Event
        var events = ouvrage.pullDomainEvents();
        assertThat(events).hasSize(1); // Il n'y a que l'événement d'ajout car le get inMemoryDb ne simule pas la recréation
        assertThat(events.get(0)).isInstanceOf(com.bibliocqrs.core.events.ExemplaireAjouteEvent.class);
        com.bibliocqrs.core.events.ExemplaireAjouteEvent event = (com.bibliocqrs.core.events.ExemplaireAjouteEvent) events.get(0);
        assertThat(event.salle()).isEqualTo(salle);
    }
}

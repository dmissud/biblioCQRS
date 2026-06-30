package com.bibliocqrs.command.infra.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "exemplaires")
public class ExemplaireEntity {

    @Id
    private UUID id;

    @jakarta.persistence.Column(unique = true)
    private String codeBarre;

    private String salle;
    private String etagere;
    private String position;

    protected ExemplaireEntity() {}

    public ExemplaireEntity(UUID id, String codeBarre, String salle, String etagere, String position) {
        this.id = id;
        this.codeBarre = codeBarre;
        this.salle = salle;
        this.etagere = etagere;
        this.position = position;
    }

    public UUID getId() { return id; }

    public String getCodeBarre() {
        return codeBarre;
    }
    public String getSalle() { return salle; }
    public String getEtagere() { return etagere; }
    public String getPosition() { return position; }
}

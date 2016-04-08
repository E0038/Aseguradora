package org.e38.m6.aseguradora.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CLASSE Asseguradora
 * ◦  id, farà d'identificador. JPA li assignarà un valor automàtic per cada inserció.
 * ◦ L'atribut nom s'implementarà amb 100 caràcters com a màxim.  No es pot repetir. No nul.
 * ◦ L'atribut nif o cif
 */
@Entity
public class Asseguradora implements IModelMarker, Serializable {

    @Column(nullable = false, length = 100, unique = true)
    private String nom;
    private String cif;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Asseguradora setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public Asseguradora setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getCif() {
        return cif;
    }

    public Asseguradora setCif(String cif) {
        this.cif = cif;
        return this;
    }
}

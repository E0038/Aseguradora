package org.e38.m6.aseguradora.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * ◦ id, farà d'identificador i JPA li assignarà un valor automàtic per cada inserció.(table)
 * ◦ nif, tindrà un valor únic per a cada client i una mida màxima de 9 caràcters. No nul.
 * ◦ nom, s'emmagatzemarà en una columna anomenada nomClient.
 * ◦ adreca, de tipus Adreca. Aquest atribut estarà incrustat en aquesta Entity.
 */
@Entity
@NamedQueries({@NamedQuery(name = Client.FIND_CLIENT_BY_NAME, query = "SELECT C FROM Client C WHERE  C.nom = :name"),
        @NamedQuery(name = Client.FIND_CLIENT_BY_NIF, query = "Select C FROM Client C WHERE C.nif = :nif")})
public class Client implements IModelMarker,Serializable {

    public static final String FIND_CLIENT_BY_NAME = "findClientByName";
    public static final String FIND_CLIENT_BY_NIF = "findClientByNif";
    @SuppressWarnings("DuplicateStringLiteralInspection")
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @Column(length=9, nullable = false, unique = true)
    private String nif;
    @SuppressWarnings("JpaDataSourceORMInspection")
    @Column(name = "nomClient")
    private String nom;
    @Embedded
    private Adreca adreca;

    public Client() {
    }

    public String getNif() {
        return nif;
    }

    public Client setNif(String nif) {
        this.nif = nif;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public Client setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public Adreca getAdreca() {
        return adreca;
    }

    public Client setAdreca(Adreca adreca) {
        this.adreca = adreca;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Client setId(Integer id) {
        this.id = id;
        return this;
    }
}

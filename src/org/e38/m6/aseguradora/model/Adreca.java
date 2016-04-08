package org.e38.m6.aseguradora.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CLASSE Adreca no serà una Entity, estarà incrustada en les classes  Entity que estigui com atribut.
 * ◦  carrer,  amb una mida màxima de 50 caràcters
 * ◦  numero, enter.
 * ◦  poblacio,  amb una mida màxima de 50 caràcters
 */
@Embeddable
public class Adreca {
    @Column(length = 50)
    private String carrer;
    @Column(length = 50)
    private String poblacio;
    private Integer numero;

    public Adreca() {
    }

    public String getCarrer() {
        return carrer;
    }

    public Adreca setCarrer(String carrer) {
        this.carrer = carrer;
        return this;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public Adreca setPoblacio(String poblacio) {
        this.poblacio = poblacio;
        return this;
    }

    public Integer getNumero() {
        return numero;
    }

    public Adreca setNumero(Integer numero) {
        this.numero = numero;
        return this;
    }
}

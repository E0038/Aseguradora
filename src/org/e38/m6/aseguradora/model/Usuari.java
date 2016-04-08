package org.e38.m6.aseguradora.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * CLASSE Usuari, per entrar en l'aplicació.
 * ◦  nom, mida màxima de 30 caràcters
 * ◦  contrasenya, mida mínima 6 i màxima de 30 caràcters
 */
@Entity
public class Usuari implements IModelMarker,Serializable {
    @Id
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String password;

    public String getName() {
        return name;
    }

    public Usuari setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Usuari setPassword(String password) {
        this.password = password;
        return this;
    }
}

package org.e38.m6.aseguradora.control.FX.propertys;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.e38.m6.aseguradora.model.Client;

/**
 * Created by sergi on 4/12/16.
 * read only Client Property Wrapper
 */
public class ClientPropertys {
    private Client client;
    private IntegerProperty id;
    private StringProperty nif;
    private StringProperty nom;
    private StringProperty carrer;
    private StringProperty poblacio;
    private IntegerProperty numero;

    public ClientPropertys(Client client) {
        this.client = client;
        updateProperties(client);
    }

    public void updateProperties(Client newInstance) {
        this.client = newInstance;
        id = new SimpleIntegerProperty(client.getId());
        nif = new SimpleStringProperty(client.getNif());
        nom = new SimpleStringProperty(client.getNom());
        carrer = new SimpleStringProperty(client.getAdreca().getCarrer());
        poblacio = new SimpleStringProperty(client.getAdreca().getPoblacio());
        numero = new SimpleIntegerProperty(client.getAdreca().getNumero());
    }


    public Client getClient() {
        return client;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNif() {
        return nif.get();
    }

    public StringProperty nifProperty() {
        return nif;
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getCarrer() {
        return carrer.get();
    }

    public StringProperty carrerProperty() {
        return carrer;
    }

    public String getPoblacio() {
        return poblacio.get();
    }

    public StringProperty poblacioProperty() {
        return poblacio;
    }

    public int getNumero() {
        return numero.get();
    }

    public IntegerProperty numeroProperty() {
        return numero;
    }
}

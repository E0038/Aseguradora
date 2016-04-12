package org.e38.m6.aseguradora.control.FX.propertys;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import org.e38.m6.aseguradora.model.Client;

/**
 * Created by sergi on 4/12/16.
 */
public class ClientPropertys {
    private Client client;
    IntegerProperty idProperty;
    StringProperty nifProperty;
    StringProperty nomProperty;



    public ClientPropertys(Client client) {
        this.client = client;
       idProperty = new SimpleIntegerProperty(client.getId());
    }
    /*
    ◦ id, farà d'identificador i JPA li assignarà un valor automàtic per cada inserció.(table)
◦ nif, tindrà un valor únic per a cada client i una mida màxima de 9 caràcters. No nul.
◦ nom, s'emmagatzemarà en una columna anomenada nomClient.
◦ adreca, de tipus Adreca. Aquest atribut estarà incrustat en aquesta Entity.
     */
}

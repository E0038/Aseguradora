package org.e38.m6.aseguradora.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CLASSE Vehicle
 * ◦  L'atribut enter id, farà d'identificador
 * i JPA li assignarà un valor automàtic per cada inserció (seqüència)
 * ◦  La taula dels vehicles s'anomenarà VEHICLES.
 * ◦  matricula, 7 caràcters, sense repetició, no nul.
 * Crear  un index.
 * ◦  marca-model.
 * ◦  anyFabricacio, enter.
 * ◦  propietari, de tipus Client. Es recuperarà de forma diferida.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "VEHICLES", indexes = {@Index(name = "idx_matricula", columnList = Vehicle.MATRICULA_COLUMN)})
@NamedQuery(name = Vehicle.VEICLE_BY_MATRICULA,query = "SELECT V FROM Vehicle V WHERE V.matricula = :matricula")
public class Vehicle implements IModelMarker, Serializable {

    public static final String MATRICULA_COLUMN = "matricula";
    public static final String VEICLE_BY_MATRICULA = "VeicleByMatricula";
    @SuppressWarnings("DuplicateStringLiteralInspection")
    @SequenceGenerator(name = "veicle_seq", sequenceName = "seq_veicle")
    @GeneratedValue(generator = "veicle_seq", strategy = GenerationType.SEQUENCE)
    private String id;

    @Column(length = 7, nullable = false, unique = true, name = MATRICULA_COLUMN)
    private String matricula;
    private Integer anyFabricacio;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client propietari;
    private String marcaModel;

    public String getMatricula() {
        return matricula;
    }

    public Vehicle setMatricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public Integer getAnyFabricacio() {
        return anyFabricacio;
    }

    public Vehicle setAnyFabricacio(Integer anyFabricacio) {
        this.anyFabricacio = anyFabricacio;
        return this;
    }

    public Client getPropietari() {
        return propietari;
    }

    public Vehicle setPropietari(Client propietari) {
        this.propietari = propietari;
        return this;
    }

    @Id
    public String getId() {
        return id;
    }

    public Vehicle setId(String id) {
        this.id = id;
        return this;
    }
}

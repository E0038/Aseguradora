package org.e38.m6.aseguradora.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sergi on 4/6/16.
 */
@Entity
@Table(indexes = {@Index(name = "idx_prenedor", columnList = "prenedor")})
@NamedQueries(
        {@NamedQuery(name = Polissa.POLISSA_VIGENTS, query = "SELECT P FROM Polissa P WHERE P.dataFi > current_date ")
                , @NamedQuery(name = Polissa.POLISSE_BY_CLIENT, query = "SELECT P FROM Polissa P WHERE P.prenedor = :client ")
                , @NamedQuery(name = Polissa.POLISSA_BY_VEICLE, query = "SELECT P FROM Polissa P WHERE P.vehicle = :vehicle")
        })
public class Polissa implements IModelMarker, Serializable {
    public static final String POLISSA_VIGENTS = "PolissaVigents";
    public static final String POLISSE_BY_CLIENT = "PolisseByClient";
    public static final String POLISSA_BY_VEICLE = "PolissaByVeicle";
    @Id
    @SequenceGenerator(name = "polissa_seq", sequenceName = "seq_polissa")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "polissa_seq")
    private Long id;
    @Column(length = 10, unique = true)
    private String polisaNum;
    @OneToOne
    @Column(nullable = false)
    private Client prenedor;
    @Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Vehicle vehicle;
    @Column(nullable = false)
    private Calendar dataInici;
    @Column(nullable = false)
    private Calendar dataFi;
    @Enumerated(EnumType.STRING)
    private Polissa.TYPE tipus;
    private long prima;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Cobertura> cobertures;

    public Polissa() {
    }

    public String getPolisaNum() {
        return polisaNum;
    }

    public Polissa setPolisaNum(String polisaNum) {
        this.polisaNum = polisaNum;
        return this;
    }

    public Client getPrenedor() {
        return prenedor;
    }

    public Polissa setPrenedor(Client prenedor) {
        this.prenedor = prenedor;
        return this;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Polissa setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public Calendar getDataInici() {
        return dataInici;
    }

    public Polissa setDataInici(Calendar dataInici) {
        this.dataInici = dataInici;
        return this;
    }

    public Calendar getDataFi() {
        return dataFi;
    }

    public Polissa setDataFi(Calendar dataFi) {
        this.dataFi = dataFi;
        return this;
    }

    public TYPE getTipus() {
        return tipus;
    }

    public Polissa setTipus(TYPE tipus) {
        this.tipus = tipus;
        return this;
    }

    public long getPrima() {
        return prima;
    }

    public Polissa setPrima(long prima) {
        this.prima = prima;
        return this;
    }

    public List<Cobertura> getCobertures() {
        return cobertures;
    }

    public Polissa setCobertures(List<Cobertura> cobertures) {
        this.cobertures = cobertures;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Polissa setId(Long id) {
        this.id = id;
        return this;
    }

    enum TYPE {
        TERCERS, TOT_RISC;
    }

    enum Cobertura {
        ROBATORI, INCENDI, VIDRES;
    }
}

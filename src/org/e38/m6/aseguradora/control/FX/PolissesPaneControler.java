package org.e38.m6.aseguradora.control.FX;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.text.Font;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.model.Polissa;

import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 * Pòlisses:
 * inserir, modificar,
 * cerques de pòlisses d'un client, d'un vehicle i vigents.
 */
public class PolissesPaneControler implements Initializable, PanelControler {
    @FXML
    private TextField textPrima;
    @FXML
    private TableColumn<Polissa, String> colID;
    @FXML
    private TableColumn<Polissa, String> colNumPoli;
    @FXML
    private TableColumn<Polissa, String> colClientID;
    @FXML
    private TableColumn<Polissa, String> colVehicleID;
    @FXML
    private TableColumn<Polissa, String> colDataInici;
    @FXML
    private TableColumn<Polissa, String> colDataFi;
    @FXML
    private TableColumn<Polissa, String> colPrima;
    @FXML
    private TableColumn<Polissa, String> colTipus;
    @FXML
    private TableColumn<Polissa, List<Polissa.Cobertura>> colCobertures;
    @FXML
    private TableColumn<Polissa, Boolean> colCheckRobatori;
    @FXML
    private TableColumn<Polissa, Boolean> colCheckInicendi;
    @FXML
    private TableColumn<Polissa, Boolean> colCheckVidres;
    @FXML
    private ListView<Boolean> listChecks;
    @FXML
    private ListView<Polissa.Cobertura> listCovertures;
    @FXML
    private TextField txtNumPolissa;
    @FXML
    private TextField txtNifPrenedor;
    @FXML
    private TextField txtMatriculaPolissa;
    @FXML
    private Font x1;
    @FXML
    private DatePicker datePickerIniciPolissa;
    @FXML
    private DatePicker datePickerFiPolissa;
    @FXML
    private Button btnInsertPolissa;
    @FXML
    private ComboBox<Polissa.TYPE> comboTipus;
    @FXML
    private Button btnModificarPolissa;
    @FXML
    private TableView<Polissa> tablePolisses;
    @FXML
    private Button btnCercarPolissaNif;
    @FXML
    private Button btnCercarPolissaMatr;
    @FXML
    private Button btnCercarPolissaVig;
    private FxControler fxControler;
    private ObservableList<Polissa> displayPolisas = FXCollections.observableArrayList();
    private ObservableList<Polissa.Cobertura> selectedCovertures = FXCollections.observableArrayList();
    private BooleanProperty hasSelectedIdx = new SimpleBooleanProperty(false);
    private DateFormat dateFormater = DateFormat.getDateTimeInstance();
    private BooleanProperty matchingProperty = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configure();
    }

    private void configure() {
        configureControls();

        configureTable();// TODO: 4/18/16
        configureBlindings();

    }

    private void configureControls() {
        comboTipus.setItems(FXCollections.observableArrayList(Polissa.TYPE.values()));
        comboTipus.getSelectionModel().select(0);
        listCovertures.setItems(FXCollections.observableArrayList(Polissa.Cobertura.values()));
        listCovertures.setCellFactory(CheckBoxListCell.forListView(item -> {
            BooleanProperty observable = new SimpleBooleanProperty();

            observable.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selectedCovertures.add(item);
                } else {
                    // new Integer para prevenir que lanze el metodo remove(int idx) envez de remove(Object e)
                    selectedCovertures.remove(item);
                }
            });

            return observable;
        }));
        selectedCovertures.addListener((ListChangeListener<Polissa.Cobertura>) c -> hasSelectedIdx.set(!selectedCovertures.isEmpty()));
        textPrima.textProperty().addListener((observable, oldValue, newValue) ->
                matchingProperty.set(newValue.matches("[\\d]+"))
        );
    }

    private void configureTable() {

        colID.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId().toString()));
        colClientID.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPrenedor().getId().toString()));
        colPrima.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getPrima())));
        colDataFi.setCellValueFactory(param -> new SimpleStringProperty(dateFormater.format(param.getValue().getDataFi().getTime())));
        colDataInici.setCellValueFactory(param -> new SimpleStringProperty(dateFormater.format(param.getValue().getDataInici().getTime())));
        colNumPoli.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPolisaNum()));
        colTipus.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTipus().name()));
        colVehicleID.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getVehicle().getId().toString()));

        colCheckInicendi.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().getCobertures().contains(Polissa.Cobertura.INCENDI)));
        colCheckInicendi.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckInicendi));

        colCheckRobatori.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().getCobertures().contains(Polissa.Cobertura.ROBATORI)));
        colCheckRobatori.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckRobatori));

        colCheckVidres.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().getCobertures().contains(Polissa.Cobertura.VIDRES)));
        colCheckVidres.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckVidres));

        tablePolisses.setItems(displayPolisas);
    }

    private void configureBlindings() {
        btnModificarPolissa.disableProperty().bind(txtNumPolissa.textProperty().isEmpty()
                .or(txtNifPrenedor.textProperty().length().isNotEqualTo(9))
                .or(txtMatriculaPolissa.textProperty().isEmpty())
                .or(datePickerFiPolissa.valueProperty().isNull())
                .or(datePickerIniciPolissa.valueProperty().isNull())
                .or(hasSelectedIdx.not())
                .or(matchingProperty.not())
        );
        btnInsertPolissa.disableProperty().bind(txtNumPolissa.textProperty().isEmpty()
                .or(txtNifPrenedor.textProperty().length().isNotEqualTo(9))
                .or(txtMatriculaPolissa.textProperty().isEmpty())
                .or(datePickerFiPolissa.valueProperty().isNull())
                .or(datePickerIniciPolissa.valueProperty().isNull())
                .or(hasSelectedIdx.not())
                .or(matchingProperty.not())
        );
        btnCercarPolissaNif.disableProperty().bind(txtNifPrenedor.textProperty().length().isNotEqualTo(9));
        btnCercarPolissaMatr.disableProperty().bind(txtMatriculaPolissa.textProperty().isEmpty());
    }

    @Override
    public void eliminar(ActionEvent actionEvent) {

    }

    @Override
    public void search(ActionEvent actionEvent) {

    }

    @Override
    public void inserir(ActionEvent actionEvent) {
        if (datePickerIniciPolissa.getValue().isAfter(datePickerFiPolissa.getValue())) {
            fxControler.showError("La data d'inici ha de ser posterior a la data de fi");
        } else {
            Polissa polissa = createPolissaObject();

            if (fxControler.insert(polissa)) {
                fxControler.showConfirmation("Polissa insertada");
            } else {
                fxControler.showError("No s'ha pogut insertar la Polissa");
            }
        }
    }

    @Override
    public FxControler getFxControler() {
        return fxControler;
    }

    @Override
    public PanelControler setFxControler(FxControler fxControler) {
        this.fxControler = fxControler;
        return this;
    }

    private Polissa createPolissaObject() {
        Polissa polissa = new Polissa();
        Calendar inici = Calendar.getInstance();
        Calendar fi = Calendar.getInstance();

        inici.set(datePickerIniciPolissa.getValue().getYear(), datePickerIniciPolissa.getValue().getMonthValue() + 1,
                datePickerIniciPolissa.getValue().getDayOfMonth());

        fi.set(datePickerFiPolissa.getValue().getYear(), datePickerFiPolissa.getValue().getMonthValue() + 1,
                datePickerFiPolissa.getValue().getDayOfMonth());


        polissa.setPolisaNum(txtNumPolissa.getText())
                .setPrenedor(fxControler.findByClientNif(txtNifPrenedor.getText()))
                .setVehicle(fxControler.findByVeicleMatricula(txtMatriculaPolissa.getText()))
                .setDataInici(inici)
                .setDataFi(fi)
                .setTipus(comboTipus.getValue())
                .setCobertures(new ArrayList<>(selectedCovertures))
                .setPrima(Long.valueOf(textPrima.getText()));
        return polissa;
    }

    public void cercarPolisaNif(ActionEvent actionEvent) {
        if (txtNifPrenedor.getText() == null || txtNifPrenedor.getText().isEmpty()) {
            fxControler.showError("Introdueix un NIF vàlid");
        } else {
            displayPolisas.clear();
            displayPolisas.addAll(fxControler.polissaByClientNif(txtNifPrenedor.getText()));
            tablePolisses.refresh();
        }

    }

    public void cercarPolissaMatr(ActionEvent actionEvent) {
        if (txtMatriculaPolissa.getText() == null || txtMatriculaPolissa.getText().isEmpty()) {
            fxControler.showError("Introdueix una matricula vàlida");
        } else {
            displayPolisas.clear();
            displayPolisas.addAll(fxControler.polissaByMatr(txtMatriculaPolissa.getText()));
            tablePolisses.refresh();
        }
    }

    public void cercarPolissaVig(ActionEvent actionEvent) {
        displayPolisas.clear();
        displayPolisas.addAll(fxControler.polissafindVigents());
        tablePolisses.refresh();
    }

    public void updatePolissa() {
        if (datePickerIniciPolissa.getValue().isAfter(datePickerFiPolissa.getValue())) {
            fxControler.showError("La data d'inici ha de ser posterior a la data de fi");
        } else {

            Polissa polissa = createPolissaObject();

            if (fxControler.update(polissa)) {
                fxControler.showConfirmation("Polissa modificada");
            } else {
                fxControler.showError("No s'ha pogut modificar la Polissa");
            }
        }
    }
}

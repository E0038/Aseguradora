package org.e38.m6.aseguradora.control.FX;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.text.Font;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.model.Polissa;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 * Pòlisses:
 * inserir, modificar,
 * cerques de pòlisses d'un client, d'un vehicle i vigents.
 */
public class PolissesPaneControler implements Initializable, PanelControler {
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
    private ObservableList<Integer> selectedCovertures = FXCollections.observableArrayList();
    private BooleanProperty hasSelectedIdx = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configure();
    }

    private void configure() {
        comboTipus.setItems(FXCollections.observableArrayList(Polissa.TYPE.values()));
        comboTipus.getSelectionModel().select(0);
        listCovertures.setItems(FXCollections.observableArrayList(Polissa.Cobertura.values()));
        listCovertures.setCellFactory(CheckBoxListCell.forListView(item -> {
            BooleanProperty observable = new SimpleBooleanProperty();

            observable.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selectedCovertures.add(listCovertures.getItems().indexOf(item));
                } else {
                    // new Integer para prevenir que lanze el metodo remove(int idx) envez de remove(Object e)
                    selectedCovertures.remove(new Integer(listCovertures.getItems().indexOf(item)));
                }
                System.out.println(selectedCovertures);
            });

            return observable;
        }));
        selectedCovertures.addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(Change<? extends Integer> c) {
                hasSelectedIdx.set(!selectedCovertures.isEmpty());
            }
        });
        configureTable();//
        configureBlindings();

    }

    private void configureTable() {
        tablePolisses.getColumns().addAll();
        tablePolisses.setItems(displayPolisas);


    }

    private void configureBlindings() {
        btnModificarPolissa.disableProperty().bind(txtNumPolissa.textProperty().isEmpty());
        btnInsertPolissa.disableProperty().bind(txtNumPolissa.textProperty().isEmpty()
                .or(txtNifPrenedor.textProperty().length().isEqualTo(9).not())
                .or(txtMatriculaPolissa.textProperty().isEmpty())
                .or(hasSelectedIdx.not())
        );

    }

    private Polissa getInputInstance() {
        return null;
    }


    @Override
    public void eliminar(ActionEvent actionEvent) {

    }

    @Override
    public void search(ActionEvent actionEvent) {

    }

    @Override
    public void inserir(ActionEvent actionEvent) {

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
}

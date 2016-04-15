package org.e38.m6.aseguradora.control.FX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.model.Polissa;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class PolissesPaneControler implements Initializable, PanelControler {
    @FXML
    private TextField txtNumPolissa;
    @FXML
    private TextField txtNifPrenedor;
    @FXML
    private TextField txtMatriculaPolissa;
    @FXML
    private Font x1;
    @FXML
    private DatePicker DatePickerIniciPolissa;
    @FXML
    private DatePicker DatePickerFiPolissa;
    @FXML
    private Button btnInsertPolissa;
    @FXML
    private ComboBox<String> comboTipus;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configure();
    }

    private void configure() {
        comboTipus.setItems(FXCollections.observableArrayList(Polissa.TYPE.TERCERS.name(), Polissa.TYPE.TOT_RISC.name()));
        comboTipus.getSelectionModel().select(0);

//        FxControler.configureReadOnlyTableByClass(tablePolisses, Polissa.class);
        tablePolisses.setItems(displayPolisas);
    }

    private Polissa getInputInstance(){
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
        return this;
    }
}

package org.e38.m6.aseguradora.control.FX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import org.e38.m6.aseguradora.control.FxControler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class PolissesPaneControler implements Initializable,PanelControler {
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
    private ComboBox comboTipus;
    @FXML
    private Button btnModificarPolissa;
    @FXML
    private TableView tablePolisses;
    @FXML
    private Button btnCercarPolissaNif;
    @FXML
    private Button btnCercarPolissaMatr;
    @FXML
    private Button btnCercarPolissaVig;
    private FxControler fxControler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

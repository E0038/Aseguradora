package org.e38.m6.aseguradora.control.FX;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class PolissesPaneControler implements Initializable {
    public TextField txtNumPolissa;
    public TextField txtNifPrenedor;
    public TextField txtMatriculaPolissa;
    public Font x1;
    public DatePicker DatePickerIniciPolissa;
    public DatePicker DatePickerFiPolissa;
    public Button btnInsertPolissa;
    public ComboBox comboTipus;
    public Button btnModificarPolissa;
    public TableView tablePolisses;
    public Button btnCercarPolissaNif;
    public Button btnCercarPolissaMatr;
    public Button btnCercarPolissaVig;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

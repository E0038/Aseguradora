package org.e38.m6.aseguradora.control.FX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class VehiclesPaneControler implements Initializable,IPaneControler {
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtNif;
    @FXML
    private TextField txtAny;
    @FXML
    private Button btnCercarVehicle;
    @FXML
    private Button btnModificarVehicle;
    @FXML
    private Button btnInsertarVehicle;

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
}

package org.e38.m6.aseguradora.control.FX;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class VehiclesPaneControler implements Initializable {
    public TextField txtMatricula;
    public TextField txtMarca;
    public TextField txtNif;
    public TextField txtAny;
    public Button btnCercarVehicle;
    public Button btnModificarVehicle;
    public Button btnInsertarVehicle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

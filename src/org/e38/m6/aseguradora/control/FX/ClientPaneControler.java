package org.e38.m6.aseguradora.control.FX;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class ClientPaneControler implements Initializable {
    public TextField txtNif;
    public TextField txtNom;
    public TextField txtCarrer;
    public TextField txtNumCarrer;
    public TextField txtPoblacio;
    public Button btnInsertarClient;
    public Button btnModificarClient;
    public Button btnCercarClient;
    public TableView tableClients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

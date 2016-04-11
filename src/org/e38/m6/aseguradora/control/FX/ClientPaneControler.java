package org.e38.m6.aseguradora.control.FX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.model.Adreca;
import org.e38.m6.aseguradora.model.Client;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class ClientPaneControler implements Initializable, IPaneControler {
    @FXML
    private TextField txtNif;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtCarrer;
    @FXML
    private TextField txtNumCarrer;
    @FXML
    private TextField txtPoblacio;
    @FXML
    private Button btnInsertarClient;
    @FXML
    private Button btnModificarClient;
    @FXML
    private Button btnCercarClient;
    @FXML
    private TableView tableClients;

    private FxControler fxControler;


    @Override
    public void eliminar(ActionEvent actionEvent) {

    }

    @Override
    public void search(ActionEvent actionEvent) {

    }

    @Override
    public void inserir(ActionEvent actionEvent) {
        String nif = txtNif.getText(), nom = txtNom.getText(),
                carre = txtCarrer.getText(), numCarrer = txtNumCarrer.getText(),
                poblacio = txtPoblacio.getText();
        Client client = new Client();
        client.setNif(nif).setNom(nom)
                .setAdreca(new Adreca()
                        .setCarrer(carre)
                        .setNumero(Integer.valueOf(numCarrer))
                        .setPoblacio(poblacio));
        if (!fxControler.getCommonControler().insert(client)) {
            fxControler.showError("No se pudo insertar el cliente");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnInsertarClient.setOnAction(this::inserir);
        btnModificarClient.setOnAction(this::eliminar);
        btnCercarClient.setOnAction(this::search);
    }


    public TextField getTxtNif() {
        return txtNif;
    }

    public ClientPaneControler setTxtNif(TextField txtNif) {
        this.txtNif = txtNif;
        return this;
    }

    public TextField getTxtNom() {
        return txtNom;
    }

    public ClientPaneControler setTxtNom(TextField txtNom) {
        this.txtNom = txtNom;
        return this;
    }

    public TextField getTxtCarrer() {
        return txtCarrer;
    }

    public ClientPaneControler setTxtCarrer(TextField txtCarrer) {
        this.txtCarrer = txtCarrer;
        return this;
    }

    public TextField getTxtNumCarrer() {
        return txtNumCarrer;
    }

    public ClientPaneControler setTxtNumCarrer(TextField txtNumCarrer) {
        this.txtNumCarrer = txtNumCarrer;
        return this;
    }

    public TextField getTxtPoblacio() {
        return txtPoblacio;
    }

    public ClientPaneControler setTxtPoblacio(TextField txtPoblacio) {
        this.txtPoblacio = txtPoblacio;
        return this;
    }

    public Button getBtnInsertarClient() {
        return btnInsertarClient;
    }

    public ClientPaneControler setBtnInsertarClient(Button btnInsertarClient) {
        this.btnInsertarClient = btnInsertarClient;
        return this;
    }

    public Button getBtnModificarClient() {
        return btnModificarClient;
    }

    public ClientPaneControler setBtnModificarClient(Button btnModificarClient) {
        this.btnModificarClient = btnModificarClient;
        return this;
    }

    public Button getBtnCercarClient() {
        return btnCercarClient;
    }

    public ClientPaneControler setBtnCercarClient(Button btnCercarClient) {
        this.btnCercarClient = btnCercarClient;
        return this;
    }

    public TableView getTableClients() {
        return tableClients;
    }

    public ClientPaneControler setTableClients(TableView tableClients) {
        this.tableClients = tableClients;
        return this;
    }

    public FxControler getFxControler() {
        return fxControler;
    }

    public ClientPaneControler setFxControler(FxControler fxControler) {
        this.fxControler = fxControler;
        return this;
    }


}

package org.e38.m6.aseguradora.control.FX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.model.Adreca;
import org.e38.m6.aseguradora.model.Client;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class ClientPaneControler implements Initializable, PanelControler {
    public TableColumn col_nif;
    public TableColumn col_nom;
    public TableColumn col_carre;
    public TableColumn col_numero;
    public TableColumn col_poblacio;
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
    private Button btnEliminarClient;
    @FXML
    private Button btnCercarClient;
    @FXML
    private TableView<Client> tableClients;

    private FxControler fxControler;
    private TextInputDialog seachInput;
    private ObservableList<Client> displayClients;


    @Override
    public void eliminar(ActionEvent actionEvent) {
        if (!fxControler.delete(getInputClient()))
            fxControler.showError("No se pudo eliminar el cliente");
    }

    @Override
    public void search(ActionEvent actionEvent) {
        Optional<String> result = seachInput.showAndWait();
        result.ifPresent(this::fillTableViewWithName);
    }

    @Override
    public void inserir(ActionEvent actionEvent) {
        Client client = getInputClient();
        if (!fxControler.insert(client)) {
            fxControler.showError("No se pudo insertar el cliente");
        }
    }

    private Client getInputClient() {
        String nif = txtNif.getText(), nom = txtNom.getText(),
                carre = txtCarrer.getText(), numCarrer = txtNumCarrer.getText(),
                poblacio = txtPoblacio.getText();
        Client client = new Client();
        client.setNif(nif).setNom(nom)
                .setAdreca(new Adreca()
                        .setCarrer(carre)
                        .setNumero(Integer.valueOf(numCarrer))
                        .setPoblacio(poblacio));
        return client;
    }

    private void fillTableViewWithName(String name) {
        Client client = fxControler.findByClientName(name);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configure();
    }

    private void configure() {
        btnInsertarClient.setOnAction(this::inserir);
        btnEliminarClient.setOnAction(this::eliminar);
        btnCercarClient.setOnAction(this::search);

        seachInput = new TextInputDialog("");
        seachInput.setTitle("Search Clients");
        seachInput.setHeaderText("Introduce client name: ");
        seachInput.setContentText("Enter your name client:");

        displayClients = FXCollections.observableArrayList();
        configureTable();
    }

    private void configureTable() {
        tableClients.setEditable(false);
//        col_nif.setCellFactory(new Callback<TableColumn, TableCell>() {
//
//        });
        tableClients.setItems(displayClients);
    }

    public FxControler getFxControler() {
        return fxControler;
    }

    public ClientPaneControler setFxControler(FxControler fxControler) {
        this.fxControler = fxControler;
        return this;
    }

    public TableView<Client> getTableClients() {
        return tableClients;
    }

    public ClientPaneControler setTableClients(TableView<Client> tableClients) {
        this.tableClients = tableClients;
        return this;
    }
}

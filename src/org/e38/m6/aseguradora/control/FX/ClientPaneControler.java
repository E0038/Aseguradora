package org.e38.m6.aseguradora.control.FX;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class ClientPaneControler implements Initializable, PanelControler {
    public TableColumn<Client, String> col_nif;
    public TableColumn<Client, String> col_nom;
    public TableColumn<Client, String> col_carre;
    public TableColumn<Client, Integer> col_numero;
    public TableColumn<Client, String> col_poblacio;
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

    @Override
    public FxControler getFxControler() {
        return fxControler;
    }

    @Override
    public ClientPaneControler setFxControler(FxControler fxControler) {
        this.fxControler = fxControler;
        return this;
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
        List<Client> clients = fxControler.findByClientName(name);
        displayClients.clear();
        displayClients.addAll(clients);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayClients = FXCollections.observableArrayList();
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

        configureTable();
    }

    private void configureTable() {
        tableClients.setEditable(false);
        col_nif.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNif()));
        col_nom.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom()));
        col_carre.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdreca().getCarrer()));
        col_poblacio.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdreca().getPoblacio()));
        col_numero.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getAdreca().getNumero()).asObject());
        tableClients.setItems(displayClients);
    }
}

package org.e38.m6.aseguradora.control.FX;

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

import javax.management.Notification;
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
    public TableColumn<Client, String> col_numero;
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
    private ObservableList<Client> displayClients = FXCollections.observableArrayList();


    @Override
    public void eliminar(ActionEvent actionEvent) {
        if (!fxControler.delete(getInputClient())){
            fxControler.showError("No s'ha pogut eliminar el client");
        }else{
            fxControler.showConfirmation("Client eliminat");
        }
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
            fxControler.showError("No s'ha pogut insertar el client");
        }else {
            fxControler.showError("Client insertat");
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
                        .setCarrer(carre.isEmpty() ? null : carre)
                        .setPoblacio(poblacio.isEmpty() ? null : poblacio));
        if (!numCarrer.isEmpty()) {
            try {
                client.getAdreca().setNumero(Integer.valueOf(numCarrer));
            } catch (NumberFormatException ignored) {
            }
        }
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
        configureBlindings();
    }

    private void configureTable() {
        tableClients.setEditable(false);
        tableClients.setItems(displayClients);
        col_nif.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNif()));
        col_nom.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom()));
        col_carre.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdreca() != null ? param.getValue().getAdreca().getCarrer() : ""));
        col_poblacio.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdreca() != null ? param.getValue().getAdreca().getPoblacio() : ""));
        col_numero.setCellValueFactory(param ->
                new SimpleStringProperty(
                        param.getValue().getAdreca() != null ?
                                param.getValue().getAdreca().getNumero() != null ?
                                        param.getValue().getAdreca().getNumero().toString() : "" : ""));
    }

    private void configureBlindings() {
        btnEliminarClient.disableProperty().bind(txtNif.textProperty().isNull().or(txtNif.textProperty().length().isEqualTo(9).not()));
        btnInsertarClient.disableProperty().bind(txtNif.textProperty().isNull()
                        .or(txtNif.textProperty().length().isEqualTo(9).not())
                        .or(txtNom.textProperty().isEmpty())
                        .or(txtNumCarrer.textProperty().isNull())
        );
    }
}

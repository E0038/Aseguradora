package org.e38.m6.aseguradora.control.FX;

import com.sun.rowset.internal.Row;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.model.Client;
import org.e38.m6.aseguradora.model.Vehicle;
import org.e38.m6.aseguradora.persistance.NoSuchEntityExeception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class VehiclesPaneControler implements Initializable, PanelControler {

    @FXML
    private ToggleGroup select;
    @FXML
    private Button btnCercar;
    FxControler fx;
    @FXML
    private TableView tableVehiclesClients;
    @FXML
    private RadioButton radioClients;
    @FXML
    private RadioButton radioVehicle;
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
    @FXML
    private ObservableList<Client> data;
    @FXML
    private ObservableList<Vehicle> data2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureBindings();
    }

    @Override
    public void eliminar(ActionEvent actionEvent) {

    }

    @Override
    public void search(ActionEvent actionEvent) {

    }

    @Override
    public void inserir(ActionEvent actionEvent) {
        Vehicle vehicle = createVehicleObject();

        if(fx.insert(vehicle)){
            fx.showConfirmation("Vehicle insertat");
        }else{
            fx.showError("No s'ha pogut insertar el vehicle");
        }
    }

    @Override
    public FxControler getFxControler() {
        return fx;
    }

    @Override
    public PanelControler setFxControler(FxControler fxControler) {
        fx = fxControler;
        return this;
    }

    private void configureBindings(){
        btnInsertarVehicle.disableProperty().bind(txtMatricula.textProperty().isEmpty().or(txtMatricula.textProperty().length().isNotEqualTo(9)
                            .or(txtAny.textProperty().isEmpty().or(txtAny.textProperty().isEmpty().or(txtMarca.textProperty().isEmpty()
                            .or(txtNif.textProperty().isEmpty().or(txtNif.textProperty().isEmpty())))))));

        btnModificarVehicle.disableProperty().bind(txtMatricula.textProperty().isEmpty().or(txtMatricula.textProperty().length().isNotEqualTo(9)
                .or(txtAny.textProperty().isEmpty().or(txtAny.textProperty().isEmpty().or(txtMarca.textProperty().isEmpty()
                        .or(txtNif.textProperty().isEmpty().or(txtNif.textProperty().isEmpty())))))));
    }

    public void findVehicle(ActionEvent actionEvent) {
        if (txtMatricula.getText() != null && txtMatricula.getText() != "" && txtMatricula.getText().length() == 7){
            Vehicle vehicle = fx.findByVeicleMatricula(txtMatricula.getText());
            txtMarca.setText(vehicle.getMarcaModel());
            txtAny.setText(String.valueOf(vehicle.getAnyFabricacio()));
            txtNif.setText(vehicle.getPropietari().getNif());
        }else{
            fx.showError("La matrícula és incorrecta");
        }
    }

    public void updateVehicle(ActionEvent actionEvent) {
            Vehicle vehicle = createVehicleObject();

            if(fx.update(vehicle)){
                fx.showConfirmation("Vehicle modificat");
            }else{
                fx.showError("No s'ha pogut modificar el vehicle");
            }
    }


    private Vehicle createVehicleObject() {

        Client client = fx.findByClientNif(txtNif.getText());
        Vehicle vehicle = new Vehicle();

        vehicle.setMatricula(txtMatricula.getText()).setMarcaModel(txtMarca.getText()).setMarcaModel(txtMarca.getText()
        ).setAnyFabricacio(Integer.parseInt(txtAny.getText())).setPropietari(client).setId("8");

        return vehicle;
    }

    public void fillTable(ActionEvent actionEvent) {

        data = FXCollections.observableArrayList();
        data2 = FXCollections.observableArrayList();
        if (radioClients.isSelected()) {
            TableColumn<Client,String > nif = new TableColumn("NIF");
            TableColumn<Client, String> nom = new TableColumn("Nom");
            nif.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNif()));
            nom.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom()));
            tableVehiclesClients.getColumns().clear();
            tableVehiclesClients.getColumns().addAll(nif, nom);
            tableVehiclesClients.setItems(data);

            List<Client> clients = null;
            try {
                clients = (List<Client>) fx.getDbManager().selectAll(Client.class);
                data.clear();
                data.addAll(clients);

            } catch (NoSuchEntityExeception noSuchEntityExeception) {
                noSuchEntityExeception.printStackTrace();
            }

        } else if (radioVehicle.isSelected()) {
            TableColumn matricula = new TableColumn("Matricula");
            TableColumn marca = new TableColumn("Marca y model");
            TableColumn any = new TableColumn("Any fabricació");
            TableColumn mar = new TableColumn("Marca y model");
            tableVehiclesClients.getColumns().clear();
            tableVehiclesClients.getColumns().addAll(matricula, marca, any, mar);
            tableVehiclesClients.setItems(data2);

            List<Vehicle> vehicles = null;
            try {
                vehicles = (List<Vehicle>) fx.getDbManager().selectAll(Vehicle.class);
                data2.clear();
                data2.addAll(vehicles);
            } catch (NoSuchEntityExeception noSuchEntityExeception) {
                noSuchEntityExeception.printStackTrace();
            }
        }else{
            fx.showError("Selecciona vehicles o clients");
        }
    }

    public void volcarDatosFila(Event event) {
        if (radioClients.isSelected()){
            Client client = (Client) tableVehiclesClients.getSelectionModel().getSelectedItem();
            txtNif.setText(client.getNif());

        } else if (radioVehicle.isSelected()){
            Vehicle vehicle = (Vehicle) tableVehiclesClients.getSelectionModel().getSelectedItem();
            txtMatricula.setText(vehicle.getMatricula());
            txtAny.setText(String.valueOf(vehicle.getAnyFabricacio()));
            txtMarca.setText(vehicle.getMarcaModel());
        }else{
            fx.showError("Selecciona vehicles o clients");
        }
    }
}

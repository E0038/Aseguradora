package org.e38.m6.aseguradora.control.FX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.model.Client;
import org.e38.m6.aseguradora.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/8/16.
 */
public class VehiclesPaneControler implements Initializable, IPaneControler {
    public ToggleGroup select;
    public Button btnCercar;
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

    public void findVehicle(ActionEvent actionEvent) {
        if (txtMatricula.getText() == null || txtMatricula.getText().length() != 7) {

            fx.showError("La matrícula no està completa");

        } else {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("RESOURCE_LOCAL");
            EntityManager em = emf.createEntityManager();

            Query q = em.createQuery("SELECT p FROM Vehicle p WHERE p.matricula = '" + txtMatricula.getText() + "'");
            Vehicle vehicle = (Vehicle) q.getSingleResult();

            txtMarca.setText(vehicle.getMarcaModel());
            txtAny.setText(String.valueOf(vehicle.getAnyFabricacio()));
            txtNif.setText(vehicle.getPropietari().getNif());
        }

    }

    public void updateVehicle(ActionEvent actionEvent) {
        if (txtMatricula.getText() == null || txtMatricula.getText().length() != 7
                || txtMarca.getText() == null || txtMarca.getText().isEmpty()
                || txtAny.getText() == null || txtAny.getText().isEmpty()
                || txtNif.getText() == null || txtNif.getText().length() > 9) {

            fx.showError("Faltan camps per omplir");

        } else {
            Vehicle vehicle = createVehicleObject();
            fx.update(vehicle);
        }
    }

    private Vehicle createVehicleObject() {

        Query q = fx.getDbManager().getEntityManager().createQuery("SELECT p FROM Client p WHERE p.nif = '" + txtNif.getText() + "'");
        Client client = (Client) q.getSingleResult();
        Vehicle vehicle = new Vehicle();

        vehicle.setMatricula(txtMatricula.getText()).setMarcaModel(txtMarca.getText()).setMarcaModel(txtMarca.getText()
        ).setAnyFabricacio(Integer.parseInt(txtAny.getText())).setPropietari(client);

        return vehicle;
    }

    public void insertVehicle(ActionEvent actionEvent) {
        if (txtMatricula.getText() == null || txtMatricula.getText().length() != 7
                || txtMarca.getText() == null || txtMarca.getText().isEmpty()
                || txtAny.getText() == null || txtAny.getText().isEmpty()
                || txtNif.getText() == null || txtNif.getText().length() > 9) {

            fx.showError("Faltan camps per omplir");

        } else {
            Vehicle vehicle = createVehicleObject();
            fx.insert(vehicle);
        }
    }

    @FXML
    private void fillTable(ActionEvent actionEvent) {

        Query q;
        if (radioClients.isSelected()) {
            TableColumn nif = new TableColumn("NIF");
            TableColumn nom = new TableColumn("Nom");
            tableVehiclesClients.getColumns().clear();
            tableVehiclesClients.getColumns().addAll(nif, nom);

            q = fx.getDbManager().getEntityManager().createQuery("SELECT p.nif, p.nom FROM Client p");
            tableVehiclesClients.getItems().addAll(q.getResultList());
            tableVehiclesClients.refresh();

        } else if (radioVehicle.isSelected()) {
            q = fx.getDbManager().getEntityManager().createQuery("SELECT p.nif, p.nom FROM Client p");

        }
    }
}

package org.e38.m6.aseguradora.control;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.e38.m6.aseguradora.control.FX.PanelControler;
import org.e38.m6.aseguradora.persistance.DbManager;
import org.e38.m6.aseguradora.view.fx.LoginDialog;
import org.e38.m6.aseguradora.view.fx.RegisterDialog;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.e38.m6.aseguradora.view.fx.RegisterDialog.*;

/**
 * Created by sergi on 4/6/16.
 */
public class FxControler extends CommonControler implements Initializable {
    private static final String KEY_USUARI = "USUARI";
    private static final String KEY_POLISA = "POLISA";
    private static final String KEY_VEHICLE = "VEHICLE";
    private static final String KEY_CLIENT = "CLIENT";
    private static final String KEY_ASSEGGURADORA = "ASSEGGURADORA";
    public MenuItem itemLogin;
    public MenuItem itemRegister;
    public ComboBox<String> comboSouce;
    public VBox root;
    public ScrollPane containerPanel;
    private Map<String, URL> includePanels = new HashMap<>();
    private Alert errorAlerter = new Alert(Alert.AlertType.ERROR);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapIncludes();
        configure();
    }

    private void mapIncludes() {
        includePanels.put(KEY_CLIENT, getClass().getResource("/org/e38/m6/aseguradora/recurses/layout/ClientsInc.fxml"));
        includePanels.put(KEY_POLISA, getClass().getResource("/org/e38/m6/aseguradora/recurses/layout/PolissesInc.fxml"));
        includePanels.put(KEY_VEHICLE, getClass().getResource("/org/e38/m6/aseguradora/recurses/layout/VehiclesInc.fxml"));
    }

    private void configure() {
        errorAlerter.setTitle("ERROR");
        comboSouce.setItems(FXCollections.observableArrayList(includePanels.keySet()));
        comboSouce.setOnAction(this::comboChange);
    }

    private void comboChange(javafx.event.Event actionEvent) {
        changeCategoria(comboSouce.getValue());
    }

    private void changeCategoria(String categoria) {
        try {
            FXMLLoader loader = new FXMLLoader(includePanels.get(categoria));
            Node node = loader.load();
            PanelControler controler = loader.getController();
            controler.setFxControler(this);
            containerPanel.setContent(node);
        } catch (IOException e) {
            e.printStackTrace();
            showError("error loading panel");
        }
    }

    public void showError(String msg) {
        errorAlerter.setContentText(msg);
        errorAlerter.showAndWait();
    }

    private void installDb() {
        DbManager.getInstance().prepareEnv();
    }

    public void onCloseResquest(WindowEvent windowEvent) {
        Platform.exit();
        getDbManager().getEntityManager().getTransaction().begin();
        getDbManager().getEntityManager().flush();
        getDbManager().getEntityManager().getTransaction().commit();
        System.exit(0);
    }

    public void registerAction(ActionEvent actionEvent) {
        new RegisterDialog().showAndWait().ifPresent(regMap -> {
            try {
                register(regMap.get(KEY_USERNAME), regMap.get(KEY_MAIL), regMap.get(KEY_PASSWORD));
            } catch (InvalidCredencialsException e) {
                showError(e.getMessage());
            }
        });
    }

    public void loginAction(ActionEvent actionEvent) {
        new LoginDialog().showAndWait().ifPresent(logMap -> {
            try {
                login(logMap.get(KEY_USERNAME), logMap.get(KEY_PASSWORD));
            } catch (UserNotFoundException | InvalidCredencialsException e) {
                showError("contraseña o usario incorrecto");
            }
        });
    }

    public void setCategoriaPolisa(ActionEvent actionEvent) {
        changeCategoria(KEY_POLISA);
    }

    public void setCategoriaVehicles(ActionEvent actionEvent) {
        changeCategoria(KEY_VEHICLE);
    }

    public void setCategoriaClient(ActionEvent actionEvent) {
        changeCategoria(KEY_CLIENT);
    }
}

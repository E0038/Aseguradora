package org.e38.m6.aseguradora.control;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.e38.m6.aseguradora.control.FX.PanelControler;
import org.e38.m6.aseguradora.model.IModelMarker;
import org.e38.m6.aseguradora.model.Usuari;
import org.e38.m6.aseguradora.persistance.DbManager;
import org.e38.m6.aseguradora.view.fx.LoginDialog;
import org.e38.m6.aseguradora.view.fx.RegisterDialog;

import java.io.IOException;
import java.lang.reflect.Field;
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
    public Menu menuCategoriaActiva;
    private Map<String, URL> includePanels = new HashMap<>();
    private Alert errorAlerter = new Alert(Alert.AlertType.ERROR);
    private Alert confirmationAlerter = new Alert(Alert.AlertType.INFORMATION);
    private ObjectProperty<Usuari> usuariProperty = new SimpleObjectProperty<>(null);

    @Deprecated
    public static <T extends IModelMarker> void configureReadOnlyTableByClass(TableView<T> table, Class<T> modelClass) {
        table.setEditable(false);
        for (Field field : modelClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(javax.persistence.Transient.class)) {
                TableColumn<T, String> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(param -> {
                    try {
                        T value = param.getValue();
                        Field colField = value.getClass().getDeclaredField(field.getName());
                        return new SimpleStringProperty(colField.get(value) != null ? colField.get(value).toString() : "");
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        return null;
                    }
                });
                table.getColumns().add(column);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapIncludes();
        configure();
        //noinspection DuplicateStringLiteralInspection
        containerPanel.setContent(new Label("No content"));
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
        comboSouce.disableProperty().bind(usuariProperty.isNull());
        menuCategoriaActiva.getParentMenu().disableProperty().bind(usuariProperty.isNull());
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

    public void showConfirmation(String msg) {
        confirmationAlerter.setContentText(msg);
        confirmationAlerter.showAndWait();
    }

    private void installDb() {
        DbManager.getInstance().prepareEnv();
    }

    public void onCloseResquest(WindowEvent windowEvent) {
        Platform.exit();
        try {
            getDbManager().getEntityManager().getTransaction().begin();
            getDbManager().getEntityManager().flush();
            getDbManager().getEntityManager().getTransaction().commit();
            System.exit(0);
        } catch (Throwable e) {
            System.exit(-1);
        }

    }

    public void registerAction(ActionEvent actionEvent) {
        new RegisterDialog().showAndWait().ifPresent(regMap -> {
            try {
                usuariProperty.set(register(regMap.get(KEY_USERNAME), regMap.get(KEY_MAIL), regMap.get(KEY_PASSWORD)));
            } catch (InvalidCredencialsException e) {
                showError(e.getMessage());
            }
        });
    }

    public void loginAction(ActionEvent actionEvent) {
        new LoginDialog().showAndWait().ifPresent(logMap -> {
            try {
                usuariProperty.set(login(logMap.get(KEY_USERNAME), logMap.get(KEY_PASSWORD)));
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

    public void logOut(ActionEvent actionEvent) {
        usuariProperty.set(null);
        //noinspection DuplicateStringLiteralInspection
        containerPanel.setContent(new Label("No content"));
    }

    public void showAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Asseguradora");
        alert.setHeaderText("About");
        alert.setContentText("Proyecto M6 uf2 Asseguradora");
        alert.showAndWait();
    }
}

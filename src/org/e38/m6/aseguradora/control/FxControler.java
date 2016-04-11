package org.e38.m6.aseguradora.control;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;
import org.e38.m6.aseguradora.persistance.DbManager;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by sergi on 4/6/16.
 */
public class FxControler extends CommonControler implements Initializable {
    private static final String KEY_USUARI = "USUARI";
    private static final String KEY_POLISA = "POLISA";
    private static final String KEY_VEHICLE = "VEHICLE";
    private static final String KEY_CLIENT = "CLIENT";
    private static final String KEY_ASSEGGURADORA = "ASSEGGURADORA";

    private CommonControler commonControler = new CommonControler();
    private FXMLLoader loader = new FXMLLoader();
    private Map<String, URL> includePanels = new HashMap<>();
    private Alert errorAlerter = new Alert(Alert.AlertType.ERROR);

    public CommonControler getCommonControler() {
        return commonControler;
    }

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
    }

    private void installDb() {
        DbManager.getInstance().prepareEnv();
    }

    public void onCloseResquest(WindowEvent windowEvent) {
        Platform.exit();
    }

    @Override
    public void login(String user, String password) throws UserNotFoundException, InvalidCredencialsException {

    }

    @Override
    public void register(String username, String mail, String password) throws InvalidCredencialsException {

    }

    public void showError(String msg) {
        errorAlerter.setContentText(msg);
        errorAlerter.showAndWait();
    }
}

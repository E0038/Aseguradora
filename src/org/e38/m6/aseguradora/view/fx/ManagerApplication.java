package org.e38.m6.aseguradora.view.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.e38.m6.aseguradora.control.FxControler;

/**
 * Created by sergi on 4/6/16.
 */
public class ManagerApplication extends Application {

    public static void caller(String[] args) {
        launch(args);
    }

    public static final String ASSEGURADORA_MANAGER_TITLE = "Asseguradora Manager";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/e38/m6/aseguradora/recurses/layout/ManagerContainer.fxml"));
        createAndConfigure(primaryStage, loader);
        primaryStage.show();
    }

    private static void createAndConfigure(Stage primaryStage, FXMLLoader loader) throws java.io.IOException {
        create(primaryStage, loader);
        configure(primaryStage, loader);
    }

    private static void create(Stage primaryStage, FXMLLoader loader) throws java.io.IOException {
        Parent root = loader.load();
        primaryStage.setTitle(ASSEGURADORA_MANAGER_TITLE);
        double prefWidth = root.prefWidth(0), prefHeight = root.prefHeight(0);
        primaryStage.setScene(new Scene(root, prefWidth, prefHeight));
    }

    private static void configure(Stage primaryStage, FXMLLoader loader) {
        primaryStage.getScene().getStylesheets().add("/org/e38/m6/aseguradora/recurses/style/fx/FXManager.css");
        primaryStage.setIconified(true);
//        primaryStage.getIcons().add(new Image("/org/e38/m6/aseguradora/recurses/img/icon.png"));
        FxControler controller = loader.getController();
        primaryStage.setOnCloseRequest(controller::onCloseResquest);
        primaryStage.centerOnScreen();
    }
}

package org.e38.m6.aseguradora.view.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.e38.m6.aseguradora.control.FxControler;
import org.e38.m6.aseguradora.persistance.DbManager;

import java.io.IOException;

/**
 * Created by sergi on 4/6/16.
 */
public class ManagerApplication extends Application {

    public static final String ASSEGURADORA_MANAGER_TITLE = "Asseguradora Manager";
    public static final String ASEGURADORA_WINDOW_ICON = "/org/e38/m6/aseguradora/recurses/img/icon.png";
    public static final int TIMEOUT_MILLIS = 1000 * 60 * 5;

    public static void caller(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage splash = new Stage(StageStyle.UNDECORATED);
        showSplash(splash); // mostramos una splash screen para mietras cargamos los recursos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/e38/m6/aseguradora/recurses/layout/ManagerContainer.fxml"));
        ((Label) splash.getScene().lookup("#infoMsg")).setText("loading database components...");
        new Thread(() -> {
            DbManager.getInstance();//load static context
            Platform.runLater(() -> {
                try {
                    ((Label) splash.getScene().lookup("#infoMsg")).setText("loading UI components...");
                    createAndConfigure(primaryStage, loader, splash);
                    primaryStage.show();
                    splash.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    private void showSplash(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/e38/m6/aseguradora/recurses/layout/InitSplashScreen.fxml"));
            ImageView imageView = (ImageView) root.lookup("#splashImage");
            imageView.setImage(new Image(getClass().getResource("/org/e38/m6/aseguradora/recurses/img/splash.png").openStream()));
            stage.setScene(new Scene(root, root.prefWidth(0), root.prefHeight(0)));
            stage.setIconified(true);
            stage.getIcons().add(new Image(ASEGURADORA_WINDOW_ICON));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createAndConfigure(Stage primaryStage, FXMLLoader loader, Stage splash) throws java.io.IOException {
        create(primaryStage, loader);
        configure(primaryStage, loader, splash);
    }

    private static void create(Stage primaryStage, FXMLLoader loader) throws java.io.IOException {
        Parent root = loader.load();
        primaryStage.setTitle(ASSEGURADORA_MANAGER_TITLE);
        double prefWidth = root.prefWidth(0), prefHeight = root.prefHeight(0);
        primaryStage.setScene(new Scene(root, prefWidth, prefHeight));
    }

    private static void configure(Stage primaryStage, FXMLLoader loader, Stage splash) {
        primaryStage.getScene().getStylesheets().add("/org/e38/m6/aseguradora/recurses/style/fx/FXManager.css");
        primaryStage.setIconified(true);
        primaryStage.getIcons().add(new Image(ASEGURADORA_WINDOW_ICON));
        FxControler controller = loader.getController();
        primaryStage.setOnCloseRequest(controller::onCloseResquest);
        primaryStage.centerOnScreen();
    }
}

package org.e38.m6.aseguradora.view.fx;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergi on 4/6/16.
 */
public class LoginDialog extends Dialog<Map<String, String>> {
    public static final String KEY_USERNAME = "USERNAME";
    public static final String KEY_PASSWORD = "PASSWORD";
    private final ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
    protected String title = "Login Dialog", headerText = "Enter your login data";
    private TextField password;
    private TextField username;
    private Node loginButton;

    public LoginDialog() {
        configureDialog();
    }

    protected void configureDialog() {
        setTitle(title);
        setHeaderText(headerText);
        setGraphic(new ImageView(getClass().getResource("/org/e38/m6/aseguradora/recurses/img/login.png").toString()));

// Set the button types.
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        configureGrid(grid);
        loginButton = getDialogPane().lookupButton(loginButtonType);
        configureValidation();

        getDialogPane().setContent(grid);
// Request focus on the username field by default.
        Platform.runLater(username::requestFocus);
// Convert the result to a username-password-pair when the login button is clicked.
        setResultConverter(dialogButton -> convertResult(loginButtonType, dialogButton));
    }

    protected void configureGrid(GridPane grid) {
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        username = new TextField();
        username.setPromptText("Username");
        password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
    }

    protected void configureValidation() {
        loginButton.disableProperty().bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty()));
    }

    protected Map<String, String> convertResult(ButtonType loginButtonType, ButtonType dialogButton) {
        if (dialogButton == loginButtonType) {
            Map<String, String> result = new HashMap<>();
            result.put(KEY_USERNAME, username.getText());
            result.put(KEY_PASSWORD, password.getText());
            setResult(result);
            return result;
        }
        setResult(null);
        return null;
    }

    public LoginDialog(String headerText, String title) {
        this.headerText = headerText;
        this.title = title;
        configureDialog();
    }

    public Node getLoginButton() {
        return loginButton;
    }

    public ButtonType getLoginButtonType() {
        return loginButtonType;
    }

    public TextField getPassword() {
        return password;
    }

    public TextField getUsername() {
        return username;
    }

}

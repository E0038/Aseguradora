package org.e38.m6.aseguradora.view.fx;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Map;

/**
 * Created by sergi on 4/7/16.
 */
public class RegisterDialog extends LoginDialog {
    public static final String KEY_MAIL = "MAIL";
    public static final int PASSWORD_MIN_LENGTH = 8;
    private TextField mail;


    public RegisterDialog() {
        super("Enter your data", "register Dialog");
    }

    public RegisterDialog(String headerText, String title) {
        super(headerText, title);
    }

    @Override
    protected void configureGrid(GridPane grid) {
        super.configureGrid(grid);
        grid.add(new Label("email"), 3, 0);
        mail = new TextField();
        grid.add(mail, 3, 1);
    }

    @Override
    protected void configureValidation() {
        getLoginButton().disableProperty().bind(
                getPassword().textProperty().length().lessThan(PASSWORD_MIN_LENGTH)
                        .or(getUsername().textProperty().isEmpty())
//                .or(mail.textFormatterProperty().v)
//                        .or(matchingProperty.matches("[\\w.]+[@][\\w.]+[.][\\w]{2,10}").not())
//                        .or(new SimpleBooleanProperty(mail.textProperty().get().matches("[\\w.]+[@][\\w.]+[.][\\w]{2,10}"))
        );

        mail.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean chReg = newValue.matches("[\\w.]+[@][\\w.]+[.][\\w]{2,10}");
            if (!getLoginButton().isDisabled()) {
                getLoginButton().setDisable(!chReg);
            }
            // TODO: 4/7/16 red labels inidicators
        });
    }

    @Override
    protected Map<String, String> convertResult(ButtonType loginButtonType, ButtonType dialogButton) {
        if (loginButtonType == dialogButton) {
            Map<String, String> map = super.convertResult(loginButtonType, dialogButton);
            map.put(KEY_MAIL, mail.getText().trim());
            setResult(map);
            return map;
        } else {
            setResult(null);
            return null;
        }
    }

    public TextField getMail() {
        return mail;
    }
}

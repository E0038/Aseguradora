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
        grid.add(new Label("email"), 2, 0);
        mail = new TextField();
        grid.add(mail, 2, 1);
    }

    @Override
    protected void configureValidation() {
        super.configureValidation();
//        getLoginButton().disableProperty().bind(getPassword().textProperty().length().lessThan(8));
        getPassword().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    getLoginButton().setDisable(newValue.length() < 8);
                });
        mail.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean chReg = newValue.matches("\\w+[@]\\w+[.][\\w]{2,10}");
            getLoginButton().setDisable(!chReg);
            // TODO: 4/7/16 red labels inidicators

        });
    }

    @Override
    protected Map<String, String> convertResult(ButtonType loginButtonType, ButtonType dialogButton) {
        Map<String, String> map = super.convertResult(loginButtonType, dialogButton);
        map.put(KEY_MAIL, mail.getText().trim());
        return map;
    }
}

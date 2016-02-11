package fpt.com.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Created by senycorp on 11.02.16.
 */
public class LoginDialog extends Dialog {
    public LoginDialog() {
        setTitle("Login");
        setHeaderText("Bitte melden Sie sich an!");

        initGUI();
    }

    private void initGUI() {
        // Create the username and password labels and fields:
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);


        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Initiales enable/disable für den Login-Button:
        Node loginButton = getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Enable/disable für den Login-Button abhängig von der Eingabe:
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        getDialogPane().setContent(grid);

        // Das Ergebnis der Eingabe abrufbar machen:
        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String usernameAsString = username.getText();
                String passwordAsString = password.getText();

                // Reset fields
                username.setText("");
                password.setText("");
                return new String(usernameAsString + ";" + passwordAsString);
            }
            return null;
        });

        // Den Focus auf das Textfeld für den Namen stetzen:
        Platform.runLater(() -> username.requestFocus());
    }
}

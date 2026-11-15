
package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private static final String KEY = "djonatan123456";

    @FXML
    private TextField txtKey;

    @FXML
    protected void onLoginButtonClick(ActionEvent event)
            throws IOException {

        if (KEY.equals(txtKey.getText())) {

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Login efetuado com sucesso!"
            );

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/back/menu-view.fxml"
                    )
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node)
                    event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(scene);

        } else {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Chave de acesso incorreta!"
            );
        }
    }

    private void showMessage(
            Alert.AlertType type,
            String msg
    ) {

        Alert alerta = new Alert(type);

        alerta.setTitle("Mensagem do Sistema!");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}

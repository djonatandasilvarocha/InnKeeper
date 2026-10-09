package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public void abrirCadastro(ActionEvent event) {
        try {
            // Carrega a tela de cadastro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro-view.fxml"));
            Parent root = loader.load();

            // Pega a janela (Stage) atual a partir do botão clicado
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Define a nova cena e abre
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro Admin");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sair() {
        System.exit(0);
    }
}
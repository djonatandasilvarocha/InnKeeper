package com.example.back;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ServicoCadastroController {

    @FXML
    private TextField txtNumeroQuarto;

    @FXML
    private TextField txtTipoServico;

    @FXML
    private TextField txtDescricao;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    public void initialize() {

        cbStatus.setItems(FXCollections.observableArrayList("Em espera", "Em andamento", "Entregue / Concluído"));
        cbStatus.getSelectionModel().selectFirst();
    }

    @FXML
    public void salvarServico(ActionEvent event) {
        String quarto = txtNumeroQuarto.getText();
        String tipoServico = txtTipoServico.getText();
        String descricao = txtDescricao.getText();
        String status = cbStatus.getValue();

        if (quarto.isEmpty() || tipoServico.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Preencha o número do quarto e o tipo de serviço!");
            return;
        }


        showAlert(Alert.AlertType.INFORMATION, "Sucesso",
                "Serviço registrado com sucesso!\n" +
                        "Quarto: " + quarto + "\n" +
                        "Serviço: " + tipoServico + "\n" +
                        "Status: " + status);

        limparCampos();
    }

    @FXML
    public void voltarMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/back/menu-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal - InnKeeper");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtNumeroQuarto.clear();
        txtTipoServico.clear();
        txtDescricao.clear();
        cbStatus.getSelectionModel().selectFirst();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
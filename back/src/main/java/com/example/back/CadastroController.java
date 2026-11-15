
package com.example.back;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class CadastroController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    private static final String URL_API =
            "http://localhost:8080/usuarios/admin";

    private static final String SECRET_KEY =
            "dmwkmwdkmfdwekm";

    @FXML
    public void salvarUsuario(ActionEvent event) {

        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText();

        if (nome.isEmpty() ||
                cpf.isEmpty() ||
                email.isEmpty() ||
                senha.isEmpty()) {

            showMessage(
                    Alert.AlertType.WARNING,
                    "Preencha todos os campos!"
            );

            return;
        }

        String jsonRequest = String.format(
                """
                {
                    "nome": "%s",
                    "email": "%s",
                    "senha": "%s",
                    "cpf": "%s",
                    "secretKey": "%s"
                }
                """,
                escaparJson(nome),
                escaparJson(email),
                escaparJson(senha),
                escaparJson(cpf),
                escaparJson(SECRET_KEY)
        );

        try {

            int respostaApi = executaMetodoAPI(
                    URL_API,
                    jsonRequest,
                    "POST"
            );

            System.out.println(
                    "Codigo HTTP: " + respostaApi
            );

            if (respostaApi >= 200 &&
                    respostaApi < 300) {

                showMessage(
                        Alert.AlertType.INFORMATION,
                        "Administrador cadastrado com sucesso!"
                );

                voltar(event);

            } else {

                showMessage(
                        Alert.AlertType.ERROR,
                        "Erro ao cadastrar administrador!\n"
                                + "Codigo HTTP: " + respostaApi
                                + "\nVerifique o terminal."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    Alert.AlertType.ERROR,
                    "Erro ao conectar com o backend:\n"
                            + e.getMessage()
            );
        }
    }

    private String escaparJson(String texto) {

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private int executaMetodoAPI(
            String url,
            String json,
            String metodoHttp
    ) throws IOException {

        HttpURLConnection connection =
                (HttpURLConnection) URI.create(url)
                        .toURL()
                        .openConnection();

        try {

            connection.setRequestMethod(metodoHttp);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);

            connection.setRequestProperty(
                    "Content-Type",
                    "application/json; charset=UTF-8"
            );

            byte[] dados = json.getBytes(
                    StandardCharsets.UTF_8
            );

            try (OutputStream os =
                         connection.getOutputStream()) {

                os.write(dados);
            }

            int codigo = connection.getResponseCode();

            InputStream resposta = codigo >= 400
                    ? connection.getErrorStream()
                    : connection.getInputStream();

            if (resposta != null) {

                try (InputStream stream = resposta) {

                    String corpo = new String(
                            stream.readAllBytes(),
                            StandardCharsets.UTF_8
                    );

                    System.out.println(
                            "Resposta da API: " + corpo
                    );
                }
            }

            return codigo;

        } finally {

            connection.disconnect();
        }
    }

    @FXML
    public void voltar(ActionEvent event)
            throws IOException {

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
        stage.show();
    }

    private void showMessage(
            Alert.AlertType type,
            String mensagem
    ) {

        Alert alerta = new Alert(type);

        alerta.setTitle("InnKeeper");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}

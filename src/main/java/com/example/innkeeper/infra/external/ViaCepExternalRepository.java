
package com.example.innkeeper.infra.external;

import com.example.innkeeper.domain.interfaces.IViaCepExternalRepository;
import com.example.innkeeper.infra.dto.ViaCepResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class ViaCepExternalRepository implements IViaCepExternalRepository {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ViaCepResponse buscarEnderecoViaCepPorCep(String cep) {

        String cepLimpo = cep == null
                ? ""
                : cep.replaceAll("[^0-9]", "");

        if (cepLimpo.length() != 8) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O CEP deve conter 8 números."
            );
        }

        String url = "https://viacep.com.br/ws/"
                + cepLimpo + "/json/";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "Erro ao consultar o serviço ViaCEP."
                );
            }

            ViaCepResponse endereco = objectMapper.readValue(
                    response.body(),
                    ViaCepResponse.class
            );

            if (Boolean.TRUE.equals(endereco.getErro())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "CEP não encontrado."
                );
            }

            return endereco;

        } catch (ResponseStatusException e) {
            throw e;

        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Não foi possível consultar o ViaCEP.",
                    e
            );
        }
    }
}

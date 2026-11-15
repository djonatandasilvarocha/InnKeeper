
package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.EnderecoResponse;
import com.example.innkeeper.domain.interfaces.IViaCepExternalRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final IViaCepExternalRepository viaCepExternalRepository;

    public EnderecoService(
            IViaCepExternalRepository viaCepExternalRepository
    ) {
        this.viaCepExternalRepository = viaCepExternalRepository;
    }

    public EnderecoResponse buscarEndereco(String cep) {

        var endereco = viaCepExternalRepository
                .buscarEnderecoViaCepPorCep(cep);

        return new EnderecoResponse(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getLocalidade(),
                endereco.getUf()
        );
    }
}

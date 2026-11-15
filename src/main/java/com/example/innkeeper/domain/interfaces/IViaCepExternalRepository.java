
package com.example.innkeeper.domain.interfaces;

import com.example.innkeeper.infra.dto.ViaCepResponse;

public interface IViaCepExternalRepository {

    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);

}

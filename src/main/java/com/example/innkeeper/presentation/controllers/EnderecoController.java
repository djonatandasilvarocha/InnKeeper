
package com.example.innkeeper.presentation.controllers;

import com.example.innkeeper.application.DTOs.EnderecoResponse;
import com.example.innkeeper.application.service.EnderecoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
@Tag(
        name = "Endereços",
        description = "Consulta de endereços pela API ViaCEP."
)
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{cep}")
    @Operation(
            summary = "Consultar endereço pelo CEP",
            description = "Busca o endereço utilizando a API externa ViaCEP."
    )
    public ResponseEntity<EnderecoResponse> buscarEndereco(
            @PathVariable String cep
    ) {
        return ResponseEntity.ok(
                enderecoService.buscarEndereco(cep)
        );
    }
}

package com.example.innkeeper.application.service;

import com.example.innkeeper.application.DTOs.LoginRequest;
import com.example.innkeeper.application.DTOs.LoginResponse;
import com.example.innkeeper.application.DTOs.UsuarioResponse;
import com.example.innkeeper.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    public LoginResponse validarUsuarioAutenticadoERetornaToken( LoginRequest loginRequest ){


        if(usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())){

            //gerar token está diferente que o do professor
            var token = tokenService.gerarToken(loginRequest.email());
            return new LoginResponse(token);
        }
        return null;
    }


    public List<UsuarioResponse> ListarTodosUsuariosTable(){

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

}
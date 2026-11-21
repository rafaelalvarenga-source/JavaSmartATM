
package com.javasmartatm.service;

import com.javasmartatm.dto.request.LoginRequest;
import com.javasmartatm.dto.response.LoginResponse;
import com.javasmartatm.security.JwtService;
import com.javasmartatm.security.UsuarioAutenticado;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse autenticar(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.cpf(),
                                request.senha()
                        )
                );

        UsuarioAutenticado usuario =
                (UsuarioAutenticado) authentication.getPrincipal();

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getNome(),
                usuario.getUsername(),
                usuario.getTipoUsuario()
        );
    }
}


package com.javasmartatm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms:3600000}") long expirationMs
    ) {
        this.signingKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        this.expirationMs = expirationMs;
    }

    public String gerarToken(UsuarioAutenticado usuario) {

        Date agora = new Date();
        Date expiracao = new Date(
                agora.getTime() + expirationMs
        );

        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("id", usuario.getId())
                .claim("tipoUsuario", usuario.getTipoUsuario().name())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(signingKey)
                .compact();
    }

    public String extrairCpf(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean validarToken(
            String token,
            UserDetails usuario
    ) {
        String cpf = extrairCpf(token);

        return cpf.equals(usuario.getUsername())
                && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        return extrairClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

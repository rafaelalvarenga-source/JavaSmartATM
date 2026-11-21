
package com.javasmartatm.security;

import com.javasmartatm.model.enums.TipoUsuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioAutenticado implements UserDetails {

    private final Long id;
    private final String nome;
    private final String cpf;
    private final String senhaHash;
    private final TipoUsuario tipoUsuario;
    private final boolean ativo;

    public UsuarioAutenticado(
            Long id,
            String nome,
            String cpf,
            String senhaHash,
            TipoUsuario tipoUsuario,
            boolean ativo
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senhaHash = senhaHash;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + tipoUsuario.name()
                )
        );
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}

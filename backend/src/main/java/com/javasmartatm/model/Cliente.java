package com.javasmartatm.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "clientes",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_clientes_cpf",
            columnNames = "cpf"
        )
    }
)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    protected Cliente() {
        // Construtor exigido pelo JPA
    }

    public Cliente(
            String cpf,
            String nome,
            String senhaHash
    ) {
        this.cpf = cpf;
        this.nome = nome;
        this.senhaHash = senhaHash;
        this.ativo = true;
    }

    @PrePersist
    protected void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
package com.javasmartatm.model;

import com.javasmartatm.model.enums.TipoUsuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "funcionarios",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_funcionarios_cpf",
            columnNames = "cpf"
        )
    }
)
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private TipoUsuario tipoUsuario;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    protected Funcionario() {
        // Construtor exigido pelo JPA
    }

    public Funcionario(
            String cpf,
            String nome,
            String senhaHash,
            TipoUsuario tipoUsuario
    ) {
        if (tipoUsuario != TipoUsuario.ATENDENTE
                && tipoUsuario != TipoUsuario.GERENTE) {
            throw new IllegalArgumentException(
                    "Funcionários devem ser ATENDENTE ou GERENTE."
            );
        }

        this.cpf = cpf;
        this.nome = nome;
        this.senhaHash = senhaHash;
        this.tipoUsuario = tipoUsuario;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
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

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
}
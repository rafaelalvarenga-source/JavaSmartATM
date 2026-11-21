
package com.javasmartatm.dto.response;

import com.javasmartatm.model.enums.TipoUsuario;

import java.time.LocalDateTime;

public record FuncionarioResponse(
        Long id,
        String cpf,
        String nome,
        TipoUsuario tipoUsuario,
        boolean ativo,
        LocalDateTime criadoEm
) {}

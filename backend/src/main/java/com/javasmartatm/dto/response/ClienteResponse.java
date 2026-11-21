package com.javasmartatm.dto.response;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String cpf,
        String nome,
        boolean ativo,
        LocalDateTime criadoEm
) {}
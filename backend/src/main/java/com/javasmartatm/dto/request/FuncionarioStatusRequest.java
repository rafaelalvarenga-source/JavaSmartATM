
package com.javasmartatm.dto.request;

import jakarta.validation.constraints.NotNull;

public record FuncionarioStatusRequest(

        @NotNull(message = "O status é obrigatório.")
        Boolean ativo

) {}

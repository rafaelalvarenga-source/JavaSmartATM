package com.javasmartatm.dto.request;

import jakarta.validation.constraints.NotNull;

public record ClienteStatusRequest(

        @NotNull(message = "O status do cliente é obrigatório.")
        Boolean ativo

) {}
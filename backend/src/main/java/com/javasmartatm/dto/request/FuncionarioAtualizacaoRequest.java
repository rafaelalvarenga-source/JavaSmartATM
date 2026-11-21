
package com.javasmartatm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FuncionarioAtualizacaoRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(
                min = 3,
                max = 120,
                message = "O nome deve ter entre 3 e 120 caracteres."
        )
        String nome

) {}

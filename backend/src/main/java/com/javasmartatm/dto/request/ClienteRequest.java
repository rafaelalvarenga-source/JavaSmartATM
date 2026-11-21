
package com.javasmartatm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(
                regexp = "\\d{11}",
                message = "O CPF deve conter exatamente 11 números."
        )
        String cpf,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(
                min = 3,
                max = 120,
                message = "O nome deve ter entre 3 e 120 caracteres."
        )
        String nome,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(
                min = 8,
                max = 72,
                message = "A senha deve ter entre 8 e 72 caracteres."
        )
        String senha

) {}

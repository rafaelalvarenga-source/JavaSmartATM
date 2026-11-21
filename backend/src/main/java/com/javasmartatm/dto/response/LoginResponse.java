
package com.javasmartatm.dto.response;

import com.javasmartatm.model.enums.TipoUsuario;

public record LoginResponse(
        String token,
        String tipoToken,
        Long id,
        String nome,
        String cpf,
        TipoUsuario tipoUsuario
) {}

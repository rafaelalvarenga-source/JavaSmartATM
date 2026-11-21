
package com.javasmartatm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> tratarRegraNegocio(
            RegraNegocioException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "status", 409,
                        "erro", "Conflito",
                        "mensagem", ex.getMessage(),
                        "dataHora", LocalDateTime.now().toString()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> erros = new java.util.HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(erro ->
                erros.put(erro.getField(), erro.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "erro", "Dados inválidos",
                        "mensagens", erros,
                        "dataHora", LocalDateTime.now().toString()
                ));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
public ResponseEntity<Map<String, Object>> tratarNaoEncontrado(
        RecursoNaoEncontradoException ex
) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of(
                    "status", 404,
                    "erro", "Recurso não encontrado",
                    "mensagem", ex.getMessage(),
                    "dataHora", LocalDateTime.now().toString()
            ));
}
}

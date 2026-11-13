package com.tienda.pedidos.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(RecursoNoEncontradoException ex,
                                   HttpServletRequest req) {
        return new ApiError(404, "Not Found", ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex,
                                     HttpServletRequest req) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return new ApiError(400, "Bad Request", errores, req.getRequestURI());
    }

    // JSON mal formado o con tipos incorrectos: es un error del cliente, no del servidor
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBodyIlegible(HttpMessageNotReadableException ex,
                                       HttpServletRequest req) {
        return new ApiError(400, "Bad Request",
            "El cuerpo de la peticion no es un JSON valido.", req.getRequestURI());
    }

    // Parametro de ruta con un tipo invalido, por ejemplo /api/pedidos/abc
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleTipoInvalido(MethodArgumentTypeMismatchException ex,
                                       HttpServletRequest req) {
        return new ApiError(400, "Bad Request",
            "El parametro '" + ex.getName() + "' tiene un valor invalido: " + ex.getValue(),
            req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGeneral(Exception ex, HttpServletRequest req) throws Exception {
        // Las excepciones HTTP del propio framework (404 de ruta inexistente, 405 de
        // metodo no permitido) conservan su estado original en lugar de convertirse en 500
        if (ex instanceof ErrorResponse) {
            throw ex;
        }
        log.error("Error inesperado en {}", req.getRequestURI(), ex);
        return new ApiError(500, "Internal Server Error",
            "Error inesperado. Contactar soporte.", req.getRequestURI());
    }
}

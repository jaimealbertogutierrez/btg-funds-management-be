package com.example.demo.infrastructure.adapters.in.web.exception;

import com.example.demo.domain.exception.BusinessValidationException;
import com.example.demo.domain.exception.InsufficientBalanceException;
import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.infrastructure.adapters.in.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <h2>Manejador Global de Excepciones: GlobalExceptionHandler</h2>
 *
 * <p>
 * Estrategia centralizada para capturar y manejar todas las excepciones que ocurren
 * en los controladores web del microservicio. Utiliza {@link ControllerAdvice} para
 * interceptar errores y devolver respuestas HTTP consistentes al cliente.
 * </p>
 *
 * <h3>Objetivos y Buenas Prácticas:</h3>
 * <ul>
 *     <li><b>Centralización de la gestión de errores:</b> evita la duplicación de código de manejo de excepciones en cada controlador.</li>
 *     <li><b>Desacoplamiento:</b> la lógica de negocio no necesita preocuparse por la transformación de excepciones en respuestas HTTP.</li>
 *     <li><b>Respuestas consistentes:</b> garantiza que todos los errores devuelvan un formato uniforme ({@link ErrorResponse}) que los clientes pueden procesar de manera predecible.</li>
 *     <li><b>Separación de responsabilidades:</b> la capa de presentación (web) se encarga solo de traducir errores del dominio a códigos HTTP adecuados, manteniendo el dominio independiente de Spring.</li>
 *     <li><b>Trazabilidad y depuración:</b> los códigos HTTP y mensajes específicos facilitan auditoría, monitoreo y logs centralizados.</li>
 * </ul>
 *
 * <h3>Excepciones tratadas:</h3>
 * <ul>
 *     <li>{@link InsufficientBalanceException} → HTTP 400 Bad Request</li>
 *     <li>{@link BusinessValidationException} → HTTP 400 Bad Request</li>
 *     <li>{@link ResourceNotFoundException} → HTTP 404 Not Found</li>
 *     <li>{@link Exception} (cualquier otra) → HTTP 500 Internal Server Error</li>
 * </ul>
 *
 * <p>
 * Esta estrategia permite a los controladores enfocarse únicamente en la lógica de negocio
 * y delegar la gestión de errores a un único punto, mejorando mantenibilidad y escalabilidad.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de saldo insuficiente para suscripciones de fondos.
     * Devuelve un {@link ErrorResponse} con HTTP 400.
     *
     * @param ex Excepción de saldo insuficiente
     * @return ResponseEntity con la información del error
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores de validación de negocio (ej. cliente ya suscrito).
     * Devuelve un {@link ErrorResponse} con HTTP 400.
     *
     * @param ex Excepción de validación de negocio
     * @return ResponseEntity con la información del error
     */
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessValidation(BusinessValidationException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Business Validation Error",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores cuando un recurso no se encuentra (cliente o fondo inexistente).
     * Devuelve un {@link ErrorResponse} con HTTP 404.
     *
     * @param ex Excepción de recurso no encontrado
     * @return ResponseEntity con la información del error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja cualquier otra excepción no controlada.
     * Devuelve un {@link ErrorResponse} con HTTP 500.
     *
     * @param ex Excepción general
     * @return ResponseEntity con la información del error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocurrió un error inesperado en el servidor."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

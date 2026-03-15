package com.example.demo.infrastructure.adapters.in.web.dto;

import java.time.LocalDateTime;

/**
 * <h2>DTO de Respuesta de Error: ErrorResponse</h2>
 *
 * <p>
 * Representa la estructura de respuesta que el microservicio devuelve
 * cuando ocurre un error durante la ejecución de una solicitud HTTP.
 * Esta clase es utilizada en los adaptadores de entrada (web controllers)
 * para estandarizar los errores y proporcionar información clara al cliente.
 * </p>
 *
 * <h3>Responsabilidad</h3>
 * <ul>
 *     <li>Proveer información consistente de errores en las respuestas HTTP.</li>
 *     <li>Permitir que los clientes (front-end o consumidores del API) comprendan
 *         la causa del error y su contexto.</li>
 *     <li>Facilitar la trazabilidad mediante timestamp y detalles de estado.</li>
 * </ul>
 */
public class ErrorResponse {

    /**
     * Fecha y hora en que ocurrió el error, para trazabilidad y registros de auditoría.
     */
    private LocalDateTime timestamp;

    /**
     * Código de estado HTTP asociado al error (ej. 400, 404, 500).
     */
    private int status;

    /**
     * Nombre del tipo de error o categoría (ej. "Bad Request", "Not Found").
     */
    private String error;

    /**
     * Mensaje descriptivo del error, destinado a explicar la causa de la falla.
     */
    private String message;

    /**
     * Constructor principal.
     *
     * @param status Código de estado HTTP
     * @param error Nombre del tipo de error
     * @param message Mensaje descriptivo del error
     */
    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    // Getters

    /** @return Fecha y hora del error */
    public LocalDateTime getTimestamp() { return timestamp; }

    /** @return Código de estado HTTP */
    public int getStatus() { return status; }

    /** @return Nombre del tipo de error */
    public String getError() { return error; }

    /** @return Mensaje descriptivo del error */
    public String getMessage() { return message; }
}

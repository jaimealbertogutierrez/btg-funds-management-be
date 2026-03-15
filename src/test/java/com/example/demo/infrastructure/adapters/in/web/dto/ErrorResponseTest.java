package com.example.demo.infrastructure.adapters.in.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ErrorResponseTest
 *
 * <p>Pruebas unitarias de la clase {@link ErrorResponse}, verificando que:
 * <ul>
 *     <li>El constructor inicializa correctamente todos los campos.</li>
 *     <li>El timestamp se genera automáticamente.</li>
 *     <li>Los getters devuelven los valores esperados.</li>
 * </ul>
 *
 * <p>Se sigue la metodología TDD: GIVEN (precondiciones y mocks), WHEN (acción bajo prueba),
 * THEN (verificaciones/asserts).
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class ErrorResponseTest {

    /**
     * Verifica que el constructor de {@link ErrorResponse} inicialice correctamente
     * los campos y genere un timestamp automáticamente.
     *
     * <p>GIVEN: Un status, un nombre de error y un mensaje de error.
     * <p>WHEN: Se construye un objeto ErrorResponse con estos valores.
     * <p>THEN: Los campos deben coincidir con los valores proporcionados y el timestamp
     * debe generarse automáticamente y ser reciente.
     */
    @Test
    @DisplayName("Constructor: Debe inicializar todos los campos y el timestamp automáticamente")
    void constructor_ShouldInitializeFields() {
        // Arrange
        int status = 400;
        String error = "Bad Request";
        String message = "No tiene saldo disponible para vincularse al fondo DEUDAPRIVADA";

        // Act
        ErrorResponse response = new ErrorResponse(status, error, message);

        // Assert
        assertAll(
                () -> assertEquals(status, response.getStatus(), "El status debe coincidir"),
                () -> assertEquals(error, response.getError(), "El nombre del error debe coincidir"),
                () -> assertEquals(message, response.getMessage(), "El mensaje debe coincidir"),
                () -> assertNotNull(response.getTimestamp(), "El timestamp debe generarse automáticamente"),
                // Verificamos que el timestamp sea reciente (no más de 1 segundo de diferencia)
                () -> assertTrue(response.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)))
        );
    }

    /**
     * Verifica que los getters de {@link ErrorResponse} devuelvan los valores
     * asignados correctamente.
     *
     * <p>GIVEN: Un objeto ErrorResponse creado con status, nombre de error y mensaje.
     * <p>WHEN: Se llaman los getters para obtener los valores.
     * <p>THEN: Los getters deben devolver los valores proporcionados y el timestamp debe existir.
     */
    @Test
    @DisplayName("Getters: Deben retornar los valores asignados")
    void getters_ShouldReturnValues() {
        // Act
        ErrorResponse response = new ErrorResponse(404, "Not Found", "Recurso no existe");

        // Assert
        assertNotNull(response.getTimestamp(), "El timestamp no debe ser nulo");
        assertEquals(404, response.getStatus(), "El status debe coincidir");
        assertEquals("Not Found", response.getError(), "El nombre del error debe coincidir");
        assertEquals("Recurso no existe", response.getMessage(), "El mensaje debe coincidir");
    }

}

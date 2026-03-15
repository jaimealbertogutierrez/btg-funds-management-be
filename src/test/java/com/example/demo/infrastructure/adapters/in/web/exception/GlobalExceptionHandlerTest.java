package com.example.demo.infrastructure.adapters.in.web.exception;

import com.example.demo.domain.exception.BusinessValidationException;
import com.example.demo.domain.exception.InsufficientBalanceException;
import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.infrastructure.adapters.in.web.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GlobalExceptionHandlerTest
 *
 * <p>Pruebas unitarias para la clase {@link GlobalExceptionHandler}, verificando
 * que cada tipo de excepción específica y la excepción genérica sean manejadas correctamente
 * y que los {@link ResponseEntity} contengan el status y mensaje esperado.
 *
 * <p>Se aplica metodología TDD con enfoque GIVEN / WHEN / THEN para cada prueba:
 * <ul>
 *     <li><b>GIVEN:</b> La excepción a manejar.</li>
 *     <li><b>WHEN:</b> Se llama al método del handler correspondiente.</li>
 *     <li><b>THEN:</b> Se verifica que el ResponseEntity contenga status y mensaje correctos.</li>
 * </ul>
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    /**
     * Inicializa el handler antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    /**
     * Verifica que {@link GlobalExceptionHandler#handleInsufficientBalance} maneje
     * correctamente la excepción {@link InsufficientBalanceException} y devuelva
     * un status 400 con el mensaje adecuado.
     *
     * <p>GIVEN: Una excepción InsufficientBalanceException con nombre de fondo.
     * <p>WHEN: Se llama al método handleInsufficientBalance.
     * <p>THEN: Se devuelve ResponseEntity con HttpStatus.BAD_REQUEST y mensaje correcto.
     */
    @Test
    @DisplayName("Debe manejar InsufficientBalanceException con status 400")
    void handleInsufficientBalanceTest() {
        // Arrange
        String fundName = "DEUDAPRIVADA";
        InsufficientBalanceException ex = new InsufficientBalanceException(fundName);

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleInsufficientBalance(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("No tiene saldo disponible para vincularse al fondo DEUDAPRIVADA", response.getBody().getMessage());
        assertEquals("Bad Request", response.getBody().getError());
    }

    /**
     * Verifica que {@link GlobalExceptionHandler#handleBusinessValidation} maneje
     * correctamente la excepción {@link BusinessValidationException} y devuelva
     * un status 400 con el mensaje adecuado.
     *
     * <p>GIVEN: Una excepción BusinessValidationException con mensaje específico.
     * <p>WHEN: Se llama al método handleBusinessValidation.
     * <p>THEN: Se devuelve ResponseEntity con HttpStatus.BAD_REQUEST y mensaje correcto.
     */
    @Test
    @DisplayName("Debe manejar BusinessValidationException con status 400")
    void handleBusinessValidationTest() {
        // Arrange
        BusinessValidationException ex = new BusinessValidationException("Ya está suscrito");

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleBusinessValidation(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ya está suscrito", response.getBody().getMessage());
        assertEquals("Business Validation Error", response.getBody().getError());
    }

    /**
     * Verifica que {@link GlobalExceptionHandler#handleResourceNotFound} maneje
     * correctamente la excepción {@link ResourceNotFoundException} y devuelva
     * un status 404 con el mensaje adecuado.
     *
     * <p>GIVEN: Una excepción ResourceNotFoundException con mensaje específico.
     * <p>WHEN: Se llama al método handleResourceNotFound.
     * <p>THEN: Se devuelve ResponseEntity con HttpStatus.NOT_FOUND y mensaje correcto.
     */
    @Test
    @DisplayName("Debe manejar ResourceNotFoundException con status 404")
    void handleResourceNotFoundTest() {
        // Arrange
        ResourceNotFoundException ex = new ResourceNotFoundException("Cliente no encontrado");

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFound(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cliente no encontrado", response.getBody().getMessage());
    }

    /**
     * Verifica que {@link GlobalExceptionHandler#handleGeneralException} maneje
     * correctamente excepciones genéricas y devuelva un status 500 con mensaje estándar.
     *
     * <p>GIVEN: Una excepción genérica de tipo Exception.
     * <p>WHEN: Se llama al método handleGeneralException.
     * <p>THEN: Se devuelve ResponseEntity con HttpStatus.INTERNAL_SERVER_ERROR y mensaje estándar.
     */
    @Test
    @DisplayName("Debe manejar Exception genérica con status 500")
    void handleGeneralExceptionTest() {
        // Arrange
        Exception ex = new Exception("Error interno");

        // Act
        ResponseEntity<ErrorResponse> response = handler.handleGeneralException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocurrió un error inesperado en el servidor.", response.getBody().getMessage());
    }

}

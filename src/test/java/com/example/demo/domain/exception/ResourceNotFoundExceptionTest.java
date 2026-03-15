package com.example.demo.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ResourceNotFoundExceptionTest
 *
 * <p>Clase de pruebas unitarias para {@link ResourceNotFoundException}, una excepción
 * de dominio que se lanza cuando un recurso requerido (cliente, fondo, etc.) no se encuentra.
 *
 * <p>Objetivos de la clase:
 * <ul>
 *     <li>Verificar que el mensaje de error se almacena y recupera correctamente.</li>
 *     <li>Confirmar que la excepción es una {@link RuntimeException} (excepción no verificada).</li>
 *     <li>Validar que la excepción puede ser lanzada y capturada correctamente por JUnit.</li>
 * </ul>
 *
 * <p>Cada test sigue la metodología TDD y documenta de manera explícita el esquema GIVEN / WHEN / THEN.
 * Esto asegura claridad, trazabilidad y comprensión rápida del comportamiento esperado.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class ResourceNotFoundExceptionTest {

    /**
     * Test que valida que el mensaje de error se almacena correctamente en la excepción.
     *
     * <p><b>GIVEN:</b> Un mensaje de error indicando que un cliente no fue encontrado.
     * <p><b>WHEN:</b> Se crea la instancia de {@link ResourceNotFoundException} con ese mensaje.
     * <p><b>THEN:</b> El mensaje recuperado mediante getMessage() debe coincidir exactamente con el mensaje proporcionado.
     */
    @Test
    @DisplayName("Debe almacenar el mensaje de error correctamente")
    void shouldStoreMessageCorrecty() {
        // Arrange
        String errorMessage = "Cliente no encontrado con id: client-001";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage(),
                "El mensaje de la excepción debe coincidir con el proporcionado al constructor.");
    }

    /**
     * Test que valida que {@link ResourceNotFoundException} es una excepción no verificada.
     *
     * <p><b>GIVEN:</b> Ningún estado particular, solo la creación de la excepción.
     * <p><b>WHEN:</b> Se instancia la excepción.
     * <p><b>THEN:</b> Se confirma que hereda de {@link RuntimeException}.
     */
    @Test
    @DisplayName("Debe ser una instancia de RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException("Not found");

        // Assert
        assertTrue(exception instanceof RuntimeException,
                "ResourceNotFoundException debe ser una excepción unchecked.");
    }

    /**
     * Test que valida que la excepción puede ser lanzada y capturada correctamente.
     *
     * <p><b>GIVEN:</b> Un mensaje indicando que un fondo no fue encontrado.
     * <p><b>WHEN:</b> Se lanza explícitamente la excepción.
     * <p><b>THEN:</b> JUnit debe capturar la excepción correctamente y el mensaje debe coincidir.
     */
    @Test
    @DisplayName("Debe ser lanzable y capturable")
    void shouldBeThrowable() {
        // Arrange
        String msg = "Fondo no encontrado";

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException(msg);
        });

        assertEquals(msg, thrown.getMessage());
    }

}

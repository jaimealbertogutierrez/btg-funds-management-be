package com.example.demo.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * InsufficientBalanceExceptionTest
 *
 * <p>Clase de pruebas unitarias para {@link InsufficientBalanceException}, una excepción
 * de dominio que se lanza cuando un cliente intenta suscribirse a un fondo sin saldo suficiente.
 *
 * <p>Objetivos de la clase:
 * <ul>
 *     <li>Validar que el mensaje de error se construye exactamente como exige la prueba.</li>
 *     <li>Confirmar que la excepción hereda de {@link RuntimeException} (Unchecked Exception).</li>
 *     <li>Verificar que la excepción puede ser lanzada y capturada correctamente.</li>
 * </ul>
 *
 * <p>Se sigue la metodología TDD, enfatizando la claridad y la cobertura completa del comportamiento esperado.
 * Cada test documenta su lógica con el patrón GIVEN / WHEN / THEN.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class InsufficientBalanceExceptionTest {

    /**
     * Test que valida que el mensaje de la excepción se construye correctamente según el requerimiento.
     *
     * <p><b>GIVEN:</b> El nombre de un fondo para el cual el cliente no tiene saldo.
     * <p><b>WHEN:</b> Se crea la instancia de {@link InsufficientBalanceException} con dicho fondo.
     * <p><b>THEN:</b> El mensaje generado debe coincidir exactamente con el mensaje esperado en la prueba.
     */
    @Test
    @DisplayName("Debe construir el mensaje siguiendo el formato requerido por la prueba")
    void shouldFormatMessageCorrectly() {
        // Arrange
        String fundName = "DEUDAPRIVADA";
        String expectedMessage = "No tiene saldo disponible para vincularse al fondo DEUDAPRIVADA";

        // Act
        InsufficientBalanceException exception = new InsufficientBalanceException(fundName);

        // Assert
        assertEquals(expectedMessage, exception.getMessage(),
                "El mensaje de error no cumple con el formato exigido en el PDF de la prueba.");
    }

    /**
     * Test que valida que {@link InsufficientBalanceException} hereda de {@link RuntimeException}.
     *
     * <p><b>GIVEN:</b> Ningún estado particular, solo la instancia de la excepción.
     * <p><b>WHEN:</b> Se crea la instancia de la excepción.
     * <p><b>THEN:</b> Se confirma que es una subclase de {@link RuntimeException} (excepción no verificada).
     */
    @Test
    @DisplayName("Debe heredar de RuntimeException")
    void shouldBeRuntimeException() {
        // Act
        InsufficientBalanceException exception = new InsufficientBalanceException("Fondo Test");

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }

    /**
     * Test que valida que la excepción puede ser lanzada y capturada correctamente por JUnit.
     *
     * <p><b>GIVEN:</b> Un mensaje de fondo que genera la excepción.
     * <p><b>WHEN:</b> Se lanza la excepción explícitamente.
     * <p><b>THEN:</b> JUnit debe capturar la excepción correctamente.
     */
    @Test
    @DisplayName("Debe ser capturable en un bloque try-catch")
    void shouldBeThrowable() {
        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> {
            throw new InsufficientBalanceException("Fondo Error");
        });
    }

}

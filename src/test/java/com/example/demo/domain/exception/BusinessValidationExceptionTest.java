package com.example.demo.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BusinessValidationExceptionTest
 *
 * <p>Clase de pruebas unitarias para {@link BusinessValidationException}, que es una excepción
 * de negocio (unchecked) utilizada en la capa de dominio para validar reglas de negocio.
 *
 * <p>Objetivos de la clase:
 * <ul>
 *     <li>Verificar que el mensaje proporcionado al constructor se almacena correctamente.</li>
 *     <li>Confirmar que la excepción es una instancia de {@link RuntimeException} (Unchecked).</li>
 *     <li>Validar que la excepción puede ser lanzada y capturada correctamente en tests.</li>
 * </ul>
 *
 * <p>Se sigue la metodología TDD y el patrón GIVEN/WHEN/THEN para cada test, enfatizando la claridad
 * y la cobertura de comportamiento esperado de la excepción.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class BusinessValidationExceptionTest {

    /**
     * Test que valida que el mensaje pasado al constructor se almacena y se puede recuperar.
     *
     * <p><b>GIVEN:</b> Un mensaje de error de validación de negocio.
     * <p><b>WHEN:</b> Se crea una instancia de {@link BusinessValidationException} con dicho mensaje.
     * <p><b>THEN:</b> El mensaje recuperado mediante getMessage() debe ser idéntico al original.
     */
    @Test
    @DisplayName("Debe capturar y retornar el mensaje de error correctamente")
    void shouldStoreMessageCorrecty() {
        // Arrange
        String errorMessage = "El cliente ya tiene una suscripción activa a este fondo";

        // Act
        BusinessValidationException exception = new BusinessValidationException(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage(),
                "El mensaje recuperado debe ser idéntico al mensaje enviado al constructor.");
    }

    /**
     * Test que valida que {@link BusinessValidationException} hereda de {@link RuntimeException}.
     *
     * <p><b>GIVEN:</b> Ningún estado particular, solo el constructor de la excepción.
     * <p><b>WHEN:</b> Se instancia la excepción.
     * <p><b>THEN:</b> Se confirma que es una instancia de RuntimeException (excepción no verificada).
     */
    @Test
    @DisplayName("Debe ser una instancia de RuntimeException (Unchecked Exception)")
    void shouldBeInstanceOfRuntimeException() {
        // Act
        BusinessValidationException exception = new BusinessValidationException("test");

        // Assert
        assertTrue(exception instanceof RuntimeException,
                "La excepción debe heredar de RuntimeException para no obligar a capturarla con try-catch.");
    }

    /**
     * Test que valida que la excepción puede ser lanzada y capturada correctamente.
     *
     * <p><b>GIVEN:</b> Un mensaje de validación de negocio.
     * <p><b>WHEN:</b> Se lanza explícitamente {@link BusinessValidationException}.
     * <p><b>THEN:</b> JUnit captura la excepción y el mensaje coincide con el esperado.
     */
    @Test
    @DisplayName("Debe ser lanzable y capturable por JUnit")
    void shouldBeThrowable() {
        // Arrange
        String msg = "Validación de negocio fallida";

        // Act & Assert
        BusinessValidationException thrown = assertThrows(BusinessValidationException.class, () -> {
            throw new BusinessValidationException(msg);
        }, "Se esperaba que la excepción fuera lanzada.");

        assertEquals(msg, thrown.getMessage());
    }

}

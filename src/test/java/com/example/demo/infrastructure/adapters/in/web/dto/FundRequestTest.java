package com.example.demo.infrastructure.adapters.in.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FundRequestTest
 *
 * <p>Pruebas unitarias para la clase {@link FundRequest}, verificando que:
 * <ul>
 *     <li>El constructor parametrizado asigna correctamente los IDs de cliente y fondo.</li>
 *     <li>El constructor por defecto junto con los setters permite asignación manual de los campos.</li>
 *     <li>Los getters retornan los valores actuales del objeto DTO.</li>
 * </ul>
 *
 * <p>Se sigue la metodología TDD:
 * <ul>
 *     <li><b>GIVEN:</b> Precondiciones o datos iniciales para la prueba.</li>
 *     <li><b>WHEN:</b> Acción bajo prueba (constructor, setter, getter).</li>
 *     <li><b>THEN:</b> Verificaciones mediante asserts que comprueban el comportamiento esperado.</li>
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class FundRequestTest {

    /**
     * Verifica que el constructor parametrizado de {@link FundRequest} asigne
     * correctamente los IDs de cliente y fondo.
     *
     * <p>GIVEN: Un clientId y un fundId específicos.
     * <p>WHEN: Se crea un objeto FundRequest usando el constructor parametrizado.
     * <p>THEN: Los getters deben devolver los valores proporcionados.
     */
    @Test
    @DisplayName("Constructor parametrizado: Debe asignar los IDs correctamente")
    void parametricConstructor_ShouldSetFields() {
        // Arrange
        String clientId = "client-123";
        String fundId = "3";

        // Act
        FundRequest request = new FundRequest(clientId, fundId);

        // Assert
        assertAll(
                () -> assertEquals(clientId, request.getClientId(), "El ClientId debe coincidir"),
                () -> assertEquals(fundId, request.getFundId(), "El FundId debe coincidir")
        );
    }

    /**
     * Verifica que el constructor por defecto junto con setters permita
     * asignar los IDs manualmente.
     *
     * <p>GIVEN: Un objeto FundRequest vacío.
     * <p>WHEN: Se asignan los valores mediante setters.
     * <p>THEN: Los getters deben devolver los valores asignados.
     */
    @Test
    @DisplayName("Constructor por defecto y Setters: Debe permitir la asignación manual")
    void defaultConstructorAndSetters_ShouldWork() {
        // Act
        FundRequest request = new FundRequest();
        request.setClientId("user-001");
        request.setFundId("1");

        // Assert
        assertAll(
                () -> assertEquals("user-001", request.getClientId()),
                () -> assertEquals("1", request.getFundId())
        );
    }

    /**
     * Verifica que los getters de {@link FundRequest} devuelvan los valores actuales del DTO.
     *
     * <p>GIVEN: Un objeto FundRequest inicializado con IDs.
     * <p>WHEN: Se llaman los getters.
     * <p>THEN: Los getters deben devolver los valores asignados.
     */
    @Test
    @DisplayName("Getters: Deben retornar los valores actuales del DTO")
    void getters_ShouldReturnValues() {
        // Arrange
        FundRequest request = new FundRequest("C1", "F1");

        // Act & Assert
        assertNotNull(request.getClientId(), "El clientId no debe ser nulo");
        assertNotNull(request.getFundId(), "El fundId no debe ser nulo");
        assertEquals("C1", request.getClientId());
        assertEquals("F1", request.getFundId());
    }

}

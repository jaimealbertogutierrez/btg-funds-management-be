package com.example.demo.domain.ports.in;

import com.example.demo.domain.model.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * FundManagementUseCaseTest
 *
 * <p>Pruebas unitarias de contrato para el puerto de entrada {@link FundManagementUseCase}.
 * Esta clase valida que la interfaz defina correctamente los métodos requeridos por el caso de uso
 * de suscripción y cancelación de fondos.
 *
 * <p>Se enfoca en probar que el contrato de la interfaz esté completo y operativo,
 * simulando respuestas mediante mocks para ilustrar la metodología TDD.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class FundManagementUseCaseTest {

    private final FundManagementUseCase fundManagementUseCase = mock(FundManagementUseCase.class);

    /**
     * Test que valida que el puerto de entrada defina el método de suscripción a fondos.
     *
     * <p>GIVEN: Se tiene un cliente y un fondo de prueba, y un mock de la interfaz.
     * <p>WHEN: Se invoca el método {@link FundManagementUseCase#subscribeToFund(String, String)}.
     * <p>THEN: Se retorna un objeto {@link Transaction} no nulo y se verifica que el método
     *         esté definido y haya sido llamado con los parámetros correctos.
     */
    @Test
    @DisplayName("Debe validar que el puerto de entrada permita la suscripción a fondos")
    void shouldDefineSubscribeMethod() {
        // Arrange
        String clientId = "client-001";
        String fundId = "3";
        Transaction mockTransaction = new Transaction();

        when(fundManagementUseCase.subscribeToFund(clientId, fundId)).thenReturn(mockTransaction);

        // Act
        Transaction result = fundManagementUseCase.subscribeToFund(clientId, fundId);

        // Assert
        assertNotNull(result, "El método de suscripción debe estar definido en el puerto.");
        Mockito.verify(fundManagementUseCase).subscribeToFund(clientId, fundId);
    }

    /**
     * Test que valida que el puerto de entrada defina el método de cancelación de suscripciones.
     *
     * <p>GIVEN: Se tiene un cliente y un fondo de prueba, y un mock de la interfaz.
     * <p>WHEN: Se invoca el método {@link FundManagementUseCase#cancelSubscription(String, String)}.
     * <p>THEN: Se retorna un objeto {@link Transaction} no nulo y se verifica que el método
     *         esté definido y haya sido llamado con los parámetros correctos.
     */
    @Test
    @DisplayName("Debe validar que el puerto de entrada permita la cancelación de suscripciones")
    void shouldDefineCancelMethod() {
        // Arrange
        String clientId = "client-001";
        String fundId = "3";
        Transaction mockTransaction = new Transaction();

        when(fundManagementUseCase.cancelSubscription(clientId, fundId)).thenReturn(mockTransaction);

        // Act
        Transaction result = fundManagementUseCase.cancelSubscription(clientId, fundId);

        // Assert
        assertNotNull(result, "El método de cancelación debe estar definido en el puerto.");
        Mockito.verify(fundManagementUseCase).cancelSubscription(clientId, fundId);
    }

}

package com.example.demo.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TransactionTest
 *
 * <p>Clase de pruebas unitarias para {@link Transaction}, modelo de dominio que representa
 * una transacción financiera de un cliente sobre un fondo.
 * Se valida la correcta asignación de atributos mediante constructores y setters.
 *
 * <p>Se documenta cada test usando la metodología GIVEN / WHEN / THEN para claridad
 * y siguiendo buenas prácticas TDD.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class TransactionTest {

    /**
     * Test que valida que el constructor parametrizado asigna correctamente todos los campos.
     *
     * <p>GIVEN: Valores de prueba para id, clientId, fundId, tipo, monto y fecha.
     * <p>WHEN: Se crea un objeto {@link Transaction} usando el constructor parametrizado.
     * <p>THEN: Todos los getters deben retornar los valores asignados correctamente.
     */
    @Test
    @DisplayName("Constructor parametrizado: Debe asignar todos los campos correctamente")
    void parametricConstructor_ShouldSetAllFields() {
        // Arrange
        String id = "tx-123";
        String clientId = "client-001";
        String fundId = "3";
        String type = "SUBSCRIPTION";
        Double amount = 50000.0;
        LocalDateTime now = LocalDateTime.now();

        // Act
        Transaction transaction = new Transaction(id, clientId, fundId, type, amount, now);

        // Assert
        assertAll(
                () -> assertEquals(id, transaction.getId()),
                () -> assertEquals(clientId, transaction.getClientId()),
                () -> assertEquals(fundId, transaction.getFundId()),
                () -> assertEquals(type, transaction.getType()),
                () -> assertEquals(amount, transaction.getAmount()),
                () -> assertEquals(now, transaction.getDate())
        );
    }

    /**
     * Test que valida que el constructor por defecto junto con setters permite asignar manualmente los atributos.
     *
     * <p>GIVEN: Un objeto {@link Transaction} creado con constructor por defecto.
     * <p>WHEN: Se asignan valores a los atributos mediante setters.
     * <p>THEN: Los getters deben retornar los valores recién asignados.
     */
    @Test
    @DisplayName("Constructor por defecto y Setters: Debe permitir la asignación manual")
    void defaultConstructorAndSetters_ShouldWork() {
        // Act
        Transaction transaction = new Transaction();
        LocalDateTime date = LocalDateTime.of(2026, 3, 14, 20, 0);
        Double amount = 100000.0;

        transaction.setId("tx-456");
        transaction.setClientId("client-002");
        transaction.setFundId("1");
        transaction.setType("CANCELLATION");
        transaction.setAmount(amount);
        transaction.setDate(date);

        // Assert
        assertAll(
                () -> assertEquals("tx-456", transaction.getId()),
                () -> assertEquals("client-002", transaction.getClientId()),
                () -> assertEquals("1", transaction.getFundId()),
                () -> assertEquals("CANCELLATION", transaction.getType()),
                () -> assertEquals(amount, transaction.getAmount()),
                () -> assertEquals(date, transaction.getDate())
        );
    }

}

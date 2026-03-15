package com.example.demo.infrastructure.adapters.out.mongo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClientEntityTest
 *
 * <p>Pruebas unitarias para {@link ClientEntity}, verificando la correcta asignación de
 * atributos a través de constructores y setters, así como la recuperación con getters.
 *
 * <p>Se aplica la metodología GIVEN / WHEN / THEN en cada prueba:
 * <ul>
 *     <li><b>GIVEN:</b> Datos de entrada para la entidad.</li>
 *     <li><b>WHEN:</b> Se crea la entidad mediante constructor o setters.</li>
 *     <li><b>THEN:</b> Se valida que los getters devuelvan los valores esperados.</li>
 * </ul>
 */
class ClientEntityTest {

    /**
     * GIVEN: Datos de cliente completos (id, nombre, balance, transacciones, preferencia).
     * WHEN: Se crea un ClientEntity usando el constructor parametrizado.
     * THEN: Los getters deben retornar exactamente los valores proporcionados.
     */
    @Test
    @DisplayName("Debe probar el constructor con parámetros y los getters")
    void parameterizedConstructorAndGettersTest() {
        // Arrange
        String id = "client-001";
        String name = "BTG Test";
        Double balance = 500000.0;
        List<String> transactions = Arrays.asList("tx-1", "tx-2");
        String notificationPreference = "EMAIL";

        // Act
        ClientEntity entity = new ClientEntity(id, name, balance, transactions, notificationPreference);

        // Assert
        assertAll(
                () -> assertEquals(id, entity.getId(), "El ID debe coincidir"),
                () -> assertEquals(name, entity.getName(), "El nombre debe coincidir"),
                () -> assertEquals(balance, entity.getBalance(), "El balance debe coincidir"),
                () -> assertEquals(transactions, entity.getTransactions(), "La lista de transacciones debe coincidir"),
                () -> assertEquals(notificationPreference, entity.getNotificationPreference(), "La preferencia de notificación debe coincidir")
        );
    }

    /**
     * GIVEN: Datos de cliente a asignar manualmente mediante setters.
     * WHEN: Se crea un ClientEntity con constructor vacío y se asignan valores.
     * THEN: Los getters deben retornar los valores asignados.
     */
    @Test
    @DisplayName("Debe probar el constructor vacío y los setters")
    void defaultConstructorAndSettersTest() {
        // Arrange
        ClientEntity entity = new ClientEntity();
        String id = "client-002";
        String name = "Candidato";
        Double balance = 100.0;
        List<String> transactions = Arrays.asList("tx-3");
        String notificationPreference = "SMS";

        // Act
        entity.setId(id);
        entity.setName(name);
        entity.setBalance(balance);
        entity.setTransactions(transactions);
        entity.setNotificationPreference(notificationPreference);

        // Assert
        assertAll(
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(name, entity.getName()),
                () -> assertEquals(balance, entity.getBalance()),
                () -> assertEquals(transactions, entity.getTransactions()),
                () -> assertEquals(notificationPreference, entity.getNotificationPreference())
        );
    }

}

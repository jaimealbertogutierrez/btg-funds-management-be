package com.example.demo.infrastructure.adapters.out.mongo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TransactionEntityTest
 *
 * <p>Pruebas unitarias para {@link TransactionEntity}, asegurando la correcta asignación de atributos
 * mediante constructores y setters, y la recuperación con getters.
 *
 * <p>Se sigue la metodología GIVEN / WHEN / THEN en cada prueba:
 * <ul>
 *     <li><b>GIVEN:</b> Datos de entrada para la entidad.</li>
 *     <li><b>WHEN:</b> Se crea la entidad mediante constructor o setters.</li>
 *     <li><b>THEN:</b> Se valida que los getters devuelvan los valores esperados.</li>
 * </ul>
 */
class TransactionEntityTest {

    /**
     * GIVEN: Datos completos de la transacción (id, clientId, fundId, type, amount, date).
     * WHEN: Se crea TransactionEntity usando el constructor parametrizado.
     * THEN: Los getters deben retornar exactamente los valores proporcionados.
     */
    @Test
    @DisplayName("Debe probar el constructor con parámetros y todos los getters")
    void parameterizedConstructorAndGettersTest() {
        // Arrange
        String id = "uuid-test-123";
        String clientId = "client-001";
        String fundId = "3";
        String type = "SUBSCRIPTION";
        Double amount = 50000.00;
        LocalDateTime now = LocalDateTime.now();

        // Act
        TransactionEntity entity = new TransactionEntity(id, clientId, fundId, type, amount, now);

        // Assert
        assertAll(
                () -> assertEquals(id, entity.getId(), "El ID debe coincidir"),
                () -> assertEquals(clientId, entity.getClientId(), "El ID del cliente debe coincidir"),
                () -> assertEquals(fundId, entity.getFundId(), "El ID del fondo debe coincidir"),
                () -> assertEquals(type, entity.getType(), "El tipo debe coincidir"),
                () -> assertEquals(amount, entity.getAmount(), "El monto debe coincidir"),
                () -> assertEquals(now, entity.getDate(), "La fecha debe coincidir")
        );
    }

    /**
     * GIVEN: Datos de transacción para asignar manualmente mediante setters.
     * WHEN: Se crea TransactionEntity con constructor vacío y se asignan valores.
     * THEN: Los getters deben retornar los valores asignados correctamente.
     */
    @Test
    @DisplayName("Debe probar el constructor vacío y todos los setters")
    void defaultConstructorAndSettersTest() {
        // Arrange
        TransactionEntity entity = new TransactionEntity();
        String id = "uuid-test-456";
        Double amount = 100000.00;
        LocalDateTime date = LocalDateTime.of(2026, 3, 14, 20, 0);

        // Act
        entity.setId(id);
        entity.setClientId("client-002");
        entity.setFundId("1");
        entity.setType("CANCELLATION");
        entity.setAmount(amount);
        entity.setDate(date);

        // Assert
        assertAll(
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals("client-002", entity.getClientId()),
                () -> assertEquals("1", entity.getFundId()),
                () -> assertEquals("CANCELLATION", entity.getType()),
                () -> assertEquals(amount, entity.getAmount()),
                () -> assertEquals(date, entity.getDate())
        );
    }

    /**
     * GIVEN: Una fecha específica de transacción.
     * WHEN: Se asigna mediante setDate.
     * THEN: La entidad debe mantener la integridad de la fecha y ser anterior al momento actual.
     */
    @Test
    @DisplayName("Debe verificar que la entidad mantenga la integridad de la fecha")
    void dateIntegrityTest() {
        // Act
        TransactionEntity entity = new TransactionEntity();
        LocalDateTime testDate = LocalDateTime.now().minusDays(1);
        entity.setDate(testDate);

        // Assert
        assertNotNull(entity.getDate(), "La fecha no debe ser nula");
        assertTrue(entity.getDate().isBefore(LocalDateTime.now()), "La fecha debe ser anterior al momento actual");
    }

}

package com.example.demo.infrastructure.adapters.out.mongo.mapper;

import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;
import com.example.demo.domain.model.Transaction;
import com.example.demo.infrastructure.adapters.out.mongo.entity.ClientEntity;
import com.example.demo.infrastructure.adapters.out.mongo.entity.FundEntity;
import com.example.demo.infrastructure.adapters.out.mongo.entity.TransactionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MongoMappersTest
 *
 * <p>Pruebas unitarias para {@link MongoMappers}, asegurando la correcta conversión
 * entre entidades MongoDB y objetos de dominio.
 *
 * <p>Cada prueba sigue la metodología GIVEN / WHEN / THEN:
 * <ul>
 *     <li>GIVEN: Se prepara la entidad o dominio de entrada.</li>
 *     <li>WHEN: Se llama al método de mapeo.</li>
 *     <li>THEN: Se validan los resultados con assertEquals, assertNotNull o assertNull.</li>
 * </ul>
 */
class MongoMappersTest {

    // --- TESTS DE CLIENTE ---

    @Test
    @DisplayName("clientToDomain: Debe mapear correctamente y convertir Double a BigDecimal")
    void clientToDomain_ShouldMapCorrectly() {
        // GIVEN
        ClientEntity entity = new ClientEntity();
        entity.setId("C001");
        entity.setName("Tester");
        entity.setBalance(500000.0);
        entity.setTransactions(Arrays.asList("TX1"));
        entity.setNotificationPreference("SMS");

        // WHEN
        Client domain = MongoMappers.clientToDomain(entity);

        // THEN
        assertNotNull(domain);
        assertEquals("C001", domain.getId());
        assertEquals(0, Double.valueOf(500000.0).compareTo(domain.getBalance()));
        assertEquals("SMS", domain.getNotificationPreference());
        assertEquals(1, domain.getTransactions().size());
    }

    @Test
    @DisplayName("clientToDomain: Debe manejar valores nulos y asignar valores por defecto")
    void clientToDomain_ShouldHandleNulls() {
        // GIVEN
        ClientEntity entity = new ClientEntity();
        entity.setBalance(null);
        entity.setTransactions(null);
        entity.setNotificationPreference(null);

        // WHEN
        Client domain = MongoMappers.clientToDomain(entity);

        // THEN
        assertEquals(0.0, domain.getBalance(), "El balance nulo debe ser ZERO");
        assertNotNull(domain.getTransactions(), "La lista no debe ser nula");
        assertEquals("EMAIL", domain.getNotificationPreference(), "Debe defaultar a EMAIL");
    }

    @Test
    @DisplayName("toEntity (Client): Debe convertir de vuelta BigDecimal a Double")
    void clientToEntity_ShouldMapCorrectly() {
        // GIVEN
        Client domain = new Client();
        domain.setId("C001");
        domain.setBalance(Double.valueOf(1000.50));

        // WHEN
        ClientEntity entity = MongoMappers.toEntity(domain);

        // THEN
        assertEquals(1000.50, entity.getBalance());
        assertEquals("C001", entity.getId());
    }

    // --- TESTS DE FONDO ---

    @Test
    @DisplayName("toDomain (Fund): Debe mapear todos los campos")
    void fundToDomain_ShouldMap() {
        // GIVEN
        FundEntity entity = new FundEntity("3", "DEUDAPRIVADA", 50000.00, "FIC");

        // WHEN
        Fund domain = MongoMappers.toDomain(entity);

        // THEN
        assertEquals("DEUDAPRIVADA", domain.getName());
        assertEquals(50000.0, domain.getMinAmount());
    }

    // --- TESTS DE TRANSACCIÓN ---

    @Test
    @DisplayName("toDomain (Transaction): Debe mapear campos financieros y de fecha")
    void transactionToDomain_ShouldMap() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        TransactionEntity entity = new TransactionEntity("TX1", "C1", "F1", "SUBSCRIPTION", 10.00, now);

        // WHEN
        Transaction domain = MongoMappers.toDomain(entity);

        // THEN
        assertEquals(10.0, domain.getAmount());
        assertEquals(now, domain.getDate());
    }

    // --- TESTS DE NULOS TOTALES (COBERTURA DE RAMAS) ---

    @Test
    @DisplayName("Mappers: Deben retornar null si la entrada es null")
    void mappers_ShouldReturnNullWhenInputIsNull() {
        // THEN
        assertAll(
                () -> assertNull(MongoMappers.clientToDomain(null)),
                () -> assertNull(MongoMappers.toEntity((Client) null)),
                () -> assertNull(MongoMappers.toDomain((FundEntity) null)),
                () -> assertNull(MongoMappers.toEntity((Fund) null)),
                () -> assertNull(MongoMappers.toDomain((TransactionEntity) null)),
                () -> assertNull(MongoMappers.toEntity((Transaction) null))
        );
    }

}

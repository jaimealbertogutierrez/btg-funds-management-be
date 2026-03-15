package com.example.demo.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClientTest
 *
 * <p>Clase de pruebas unitarias para {@link Client}, modelo de dominio que representa
 * un cliente del sistema. La clase valida tanto la inicialización de atributos como
 * la lógica de negocio relacionada con el balance y la gestión de transacciones.
 *
 * <p>Se siguen las buenas prácticas de TDD (Test Driven Development) y se documenta
 * cada test usando el esquema GIVEN / WHEN / THEN para claridad y trazabilidad.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class ClientTest {

    /**
     * Test que valida que el constructor por defecto inicializa correctamente la lista
     * de transacciones del cliente.
     *
     * <p>GIVEN: Un cliente creado con el constructor por defecto.
     * <p>WHEN: Se consulta la lista de transacciones.
     * <p>THEN: La lista no debe ser nula y debe estar vacía.
     */
    @Test
    @DisplayName("Constructor por defecto: Debe inicializar la lista de transacciones")
    void defaultConstructor_ShouldInitializeList() {
        Client client = new Client();
        assertNotNull(client.getTransactions(), "La lista de transacciones no debe ser nula");
        assertEquals(0, client.getTransactions().size());
    }

    /**
     * Test que valida el constructor parametrizado frente a valores nulos.
     *
     * <p>GIVEN: Un cliente creado con constructor parametrizado pasando null en
     * lista de transacciones y preferencia de notificación.
     * <p>WHEN: Se consulta la lista de transacciones y la preferencia.
     * <p>THEN: La lista debe inicializarse vacía y la preferencia debe asignarse a "EMAIL".
     */
    @Test
    @DisplayName("Constructor parametrizado: Debe manejar valores nulos para listas y preferencias")
    void parametricConstructor_HandleNulls() {
        Client client = new Client("ID-1", "User", 500.0, null, null);

        assertNotNull(client.getTransactions());
        assertEquals("EMAIL", client.getNotificationPreference(), "Debe asignar EMAIL por defecto");
    }

    /**
     * Test que valida la lógica de negocio del método {@link Client#deductBalance(double)}.
     *
     * <p>GIVEN: Un cliente con balance de 500.000.
     * <p>WHEN: Se descuenta 50.000 mediante deductBalance.
     * <p>THEN: El balance final debe ser 450.000.
     */
    @Test
    @DisplayName("Lógica de Negocio: deductBalance debe restar el monto correctamente")
    void deductBalance_ShouldSubtractAmount() {
        Client client = new Client();
        client.setBalance(500000.0);

        client.deductBalance(50000.0);

        assertEquals(450000.0, client.getBalance(), "El saldo después de la deducción es incorrecto");
    }

    /**
     * Test que valida la lógica de negocio del método {@link Client#addBalance(double)}.
     *
     * <p>GIVEN: Un cliente con balance de 450.000.
     * <p>WHEN: Se suma 50.000 mediante addBalance.
     * <p>THEN: El balance final debe ser 500.000.
     */
    @Test
    @DisplayName("Lógica de Negocio: addBalance debe sumar el monto correctamente")
    void addBalance_ShouldAddAmount() {
        Client client = new Client();
        client.setBalance(450000.0);

        client.addBalance(50000.0);

        assertEquals(500000.0, client.getBalance(), "El saldo después de la adición es incorrecto");
    }

    /**
     * Test que valida que los getters y setters funcionan correctamente.
     *
     * <p>GIVEN: Un cliente creado por defecto.
     * <p>WHEN: Se asignan valores a todos los atributos mediante setters.
     * <p>THEN: Los getters deben retornar los valores correctos.
     */
    @Test
    @DisplayName("Getters y Setters: Deben asignar y recuperar todos los valores")
    void gettersAndSetters_ShouldWork() {
        Client client = new Client();
        List<String> txs = new ArrayList<>();
        txs.add("tx-001");

        client.setId("C-001");
        client.setName("John Doe");
        client.setBalance(100.0);
        client.setTransactions(txs);
        client.setNotificationPreference("SMS");

        assertAll(
                () -> assertEquals("C-001", client.getId()),
                () -> assertEquals("John Doe", client.getName()),
                () -> assertEquals(100.0, client.getBalance()),
                () -> assertEquals(1, client.getTransactions().size()),
                () -> assertEquals("SMS", client.getNotificationPreference())
        );
    }

}

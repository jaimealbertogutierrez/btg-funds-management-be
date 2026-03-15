package com.example.demo.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * FundTest
 *
 * <p>Clase de pruebas unitarias para {@link Fund}, modelo de dominio que representa
 * un fondo de inversión en el sistema. La clase valida constructores, setters y getters
 * para asegurar que los atributos se inicialicen y recuperen correctamente.
 *
 * <p>Se sigue la metodología TDD (Test Driven Development) y se documenta cada test
 * usando el esquema GIVEN / WHEN / THEN para claridad y trazabilidad.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
class FundTest {

    /**
     * Test que valida que el constructor parametrizado asigna correctamente todos los campos.
     *
     * <p>GIVEN: Valores de prueba para id, nombre, monto mínimo y categoría.
     * <p>WHEN: Se crea un objeto {@link Fund} usando el constructor parametrizado.
     * <p>THEN: Todos los getters deben retornar los valores asignados.
     */
    @Test
    @DisplayName("Constructor parametrizado: Debe asignar todos los campos correctamente")
    void parametricConstructor_ShouldSetAllFields() {
        // Arrange
        String id = "3";
        String name = "DEUDAPRIVADA";
        Double minAmount = 50000.0;
        String category = "FIC";

        // Act
        Fund fund = new Fund(id, name, minAmount, category);

        // Assert
        assertAll(
                () -> assertEquals(id, fund.getId()),
                () -> assertEquals(name, fund.getName()),
                () -> assertEquals(minAmount, fund.getMinAmount()),
                () -> assertEquals(category, fund.getCategory())
        );
    }

    /**
     * Test que valida que el constructor por defecto junto con setters permite asignar manualmente los atributos.
     *
     * <p>GIVEN: Un objeto {@link Fund} creado con constructor por defecto.
     * <p>WHEN: Se asignan valores a los atributos mediante setters.
     * <p>THEN: Los getters deben retornar los valores recién asignados.
     */
    @Test
    @DisplayName("Constructor por defecto y Setters: Debe permitir la asignación manual")
    void defaultConstructorAndSetters_ShouldWork() {
        // Act
        Fund fund = new Fund();
        fund.setId("1");
        fund.setName("FPV_BTG_PACTUAL_RECAUDADORA");
        fund.setMinAmount(75000.0);
        fund.setCategory("FPV");

        // Assert
        assertAll(
                () -> assertEquals("1", fund.getId()),
                () -> assertEquals("FPV_BTG_PACTUAL_RECAUDADORA", fund.getName()),
                () -> assertEquals(75000.0, fund.getMinAmount()),
                () -> assertEquals("FPV", fund.getCategory())
        );
    }

    /**
     * Test que valida que los getters retornan los valores actuales del objeto.
     *
     * <p>GIVEN: Un objeto {@link Fund} con un monto mínimo asignado.
     * <p>WHEN: Se consulta el atributo minAmount mediante el getter.
     * <p>THEN: El valor retornado debe coincidir con el valor asignado.
     */
    @Test
    @DisplayName("Getters: Deben retornar los valores actuales del objeto")
    void getters_ShouldReturnCurrentValues() {
        // Arrange
        Fund fund = new Fund();
        Double testAmount = 125000.0;
        fund.setMinAmount(testAmount);

        // Act & Assert
        assertEquals(testAmount, fund.getMinAmount());
    }

}

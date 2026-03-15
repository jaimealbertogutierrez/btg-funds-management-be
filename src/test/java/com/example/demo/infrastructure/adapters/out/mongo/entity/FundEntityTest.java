package com.example.demo.infrastructure.adapters.out.mongo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FundEntityTest
 *
 * <p>Pruebas unitarias para {@link FundEntity}, verificando la correcta asignación de atributos
 * a través de constructores y setters, así como la recuperación con getters.
 *
 * <p>Se aplica la metodología GIVEN / WHEN / THEN en cada prueba:
 * <ul>
 *     <li><b>GIVEN:</b> Datos de entrada para la entidad.</li>
 *     <li><b>WHEN:</b> Se crea la entidad mediante constructor o setters.</li>
 *     <li><b>THEN:</b> Se valida que los getters devuelvan los valores esperados.</li>
 * </ul>
 */
class FundEntityTest {

    /**
     * GIVEN: Datos completos de un fondo (id, nombre, monto mínimo, categoría).
     * WHEN: Se crea un FundEntity usando el constructor parametrizado.
     * THEN: Los getters deben retornar exactamente los valores proporcionados.
     */
    @Test
    @DisplayName("Debe probar el constructor con parámetros y todos los getters")
    void parameterizedConstructorAndGettersTest() {
        // Arrange
        String id = "3";
        String name = "DEUDAPRIVADA";
        Double minAmount = 50000.00;
        String category = "FIC";

        // Act
        FundEntity entity = new FundEntity(id, name, minAmount, category);

        // Assert
        assertAll(
                () -> assertEquals(id, entity.getId(), "El ID debe coincidir"),
                () -> assertEquals(name, entity.getName(), "El nombre debe coincidir"),
                () -> assertEquals(minAmount, entity.getMinAmount(), "El monto mínimo debe coincidir"),
                () -> assertEquals(category, entity.getCategory(), "La categoría debe coincidir")
        );
    }

    /**
     * GIVEN: Datos de fondo para asignar manualmente mediante setters.
     * WHEN: Se crea un FundEntity con constructor vacío y se asignan valores.
     * THEN: Los getters deben retornar los valores asignados correctamente.
     */
    @Test
    @DisplayName("Debe probar el constructor vacío y todos los setters")
    void defaultConstructorAndSettersTest() {
        // Arrange
        FundEntity entity = new FundEntity();
        String id = "1";
        String name = "FPV_BTG_PACTUAL_RECAUDADORA";
        Double minAmount = 75000.0;
        String category = "FPV";

        // Act
        entity.setId(id);
        entity.setName(name);
        entity.setMinAmount(minAmount);
        entity.setCategory(category);

        // Assert
        assertAll(
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(name, entity.getName()),
                () -> assertEquals(minAmount, entity.getMinAmount()),
                () -> assertEquals(category, entity.getCategory())
        );
    }

    /**
     * GIVEN: Un monto mínimo representado como Double.
     * WHEN: Se asigna al FundEntity mediante setMinAmount.
     * THEN: La precisión del BigDecimal se debe mantener (comparación 0).
     */
    @Test
    @DisplayName("Debe verificar que el monto mínimo maneje correctamente la precisión de BigDecimal")
    void bigDecimalPrecisionTest() {
        // Act
        FundEntity entity = new FundEntity();
        Double amount = 125000.00;
        entity.setMinAmount(amount);

        // Assert
        assertEquals(0, amount.compareTo(entity.getMinAmount()), "La precisión de BigDecimal debe mantenerse");
    }

}

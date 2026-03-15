package com.example.demo.infrastructure.adapters.out.mongo;

import com.example.demo.domain.model.Fund;
import com.example.demo.infrastructure.adapters.out.mongo.entity.FundEntity;
import com.example.demo.infrastructure.adapters.out.mongo.repository.SpringDataFundRepository;
import com.example.demo.util.TestDataFactory; // Factoría de datos de prueba
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test de integración para el adaptador de MongoDB de Fondos.
 *
 * Objetivo:
 *  - Validar la correcta interacción entre el adaptador y el repositorio Spring Data.
 *  - Asegurar el mapeo correcto entre FundEntity y Fund (modelo de dominio).
 *
 * Buenas prácticas:
 *  - Clase package-private para cumplir con estándares Sonar.
 *  - Uso de TestDataFactory para obtener datos consistentes.
 *  - Verificación de llamadas al repositorio con Mockito.
 *
 * Autor: Jaime Alberto Gutiérrez
 */
@ExtendWith(MockitoExtension.class)
class FundMongoAdapterTest {

    // --- Mocks ---
    @Mock
    private SpringDataFundRepository repository; // Repositorio de fondos en MongoDB

    // --- Clase bajo prueba ---
    @InjectMocks
    private FundMongoAdapter adapter; // Adaptador que implementa persistencia de fondos

    // --- Datos de prueba ---
    private FundEntity mockEntity;

    /**
     * Inicializa los datos de prueba antes de cada test.
     * Se usan objetos de la TestDataFactory centralizada.
     */
    @BeforeEach
    void setUp() {
        mockEntity = TestDataFactory.createMockFundEntity();
    }

    // ==========================
    // Tests de findById
    // ==========================

    @Test
    @DisplayName("findById: Debe retornar el fondo de dominio cuando existe en la base de datos")
    void findById_Success() {
        // GIVEN: el repositorio devuelve la entidad simulada
        when(repository.findById("3")).thenReturn(Optional.of(mockEntity));

        // WHEN: se llama al adaptador
        Optional<Fund> result = adapter.findById("3");

        // THEN: se obtiene el modelo de dominio correctamente
        assertTrue(result.isPresent(), "Se esperaba encontrar el fondo");
        assertEquals("3", result.get().getId(), "El ID del fondo debe coincidir");
        assertEquals("DEUDAPRIVADA", result.get().getName(), "El nombre del fondo debe coincidir");
        verify(repository, times(1)).findById("3");
    }

    @Test
    @DisplayName("findById: Debe retornar Optional vacío cuando el fondo no existe")
    void findById_NotFound() {
        // GIVEN: repositorio no encuentra el ID
        when(repository.findById("999")).thenReturn(Optional.empty());

        // WHEN
        Optional<Fund> result = adapter.findById("999");

        // THEN: se retorna un Optional vacío
        assertFalse(result.isPresent(), "No debería encontrar ningún fondo");
        verify(repository, times(1)).findById("999");
    }

}

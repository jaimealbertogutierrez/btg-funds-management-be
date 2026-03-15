package com.example.demo.infrastructure.adapters.out.mongo;

import com.example.demo.domain.model.Transaction;
import com.example.demo.infrastructure.adapters.out.mongo.entity.TransactionEntity;
import com.example.demo.infrastructure.adapters.out.mongo.repository.SpringDataTransactionRepository;
import com.example.demo.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test de integración para el adaptador de MongoDB de transacciones.
 *
 * Objetivo:
 *  - Validar la persistencia y recuperación de transacciones usando Spring Data.
 *  - Asegurar el mapeo correcto entre TransactionEntity y Transaction (modelo de dominio).
 *  - Verificar el comportamiento de la lógica de suscripción activa.
 *
 * Buenas prácticas:
 *  - Clase package-private para cumplir estándares Sonar.
 *  - Uso de TestDataFactory para obtener datos consistentes.
 *  - Verificación de llamadas al repositorio con Mockito.
 *
 * Autor: Jaime Alberto Gutiérrez
 */
@ExtendWith(MockitoExtension.class)
class TransactionMongoAdapterTest {

    // --- Mocks ---
    @Mock
    private SpringDataTransactionRepository repository;

    // --- Clase bajo prueba ---
    @InjectMocks
    private TransactionMongoAdapter adapter;

    // --- Datos de prueba ---
    private TransactionEntity mockEntity;
    private Transaction mockDomain;

    @BeforeEach
    void setUp() {
        mockEntity = TestDataFactory.createMockTransactionEntity();
        mockDomain = TestDataFactory.createMockTransactionDomain();
    }

    // ==========================
    // Test de save
    // ==========================
    @Test
    @DisplayName("save: Debe persistir la transacción y retornar el modelo de dominio")
    void save_Success() {
        // GIVEN
        when(repository.save(any(TransactionEntity.class))).thenReturn(mockEntity);

        // WHEN
        Transaction result = adapter.save(mockDomain);

        // THEN
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals("tx-999", result.getId(), "El ID de la transacción debe coincidir");
        verify(repository, times(1)).save(any(TransactionEntity.class));
    }

    // ==========================
    // Test de findByClientId
    // ==========================
    @Test
    @DisplayName("findByClientId: Debe retornar lista de transacciones mapeadas")
    void findByClientId_Success() {
        // GIVEN
        when(repository.findByClientId("client-001"))
                .thenReturn(Collections.singletonList(mockEntity));

        // WHEN
        List<Transaction> results = adapter.findByClientId("client-001");

        // THEN
        assertEquals(1, results.size(), "Debe retornar exactamente una transacción");
        assertEquals("tx-999", results.get(0).getId(), "El ID de la transacción debe coincidir");
    }

    // ==========================
    // Test de hasActiveSubscription
    // ==========================
    @Test
    @DisplayName("hasActiveSubscription: Debe retornar true si la última transacción es SUBSCRIPTION")
    void hasActiveSubscription_True() {
        // GIVEN
        when(repository.findTopByClientIdAndFundIdOrderByDateDesc("client-001", "3"))
                .thenReturn(Optional.of(mockEntity));

        // WHEN
        boolean active = adapter.hasActiveSubscription("client-001", "3");

        // THEN
        assertTrue(active, "La suscripción debe estar activa");
    }

    @Test
    @DisplayName("hasActiveSubscription: Debe retornar false si la última transacción es CANCELLATION")
    void hasActiveSubscription_False_WhenCancelled() {
        // GIVEN
        mockEntity.setType("CANCELLATION");
        when(repository.findTopByClientIdAndFundIdOrderByDateDesc("client-001", "3"))
                .thenReturn(Optional.of(mockEntity));

        // WHEN
        boolean active = adapter.hasActiveSubscription("client-001", "3");

        // THEN
        assertFalse(active, "La suscripción debe considerarse inactiva");
    }

    @Test
    @DisplayName("hasActiveSubscription: Debe retornar false si no hay transacciones previas")
    void hasActiveSubscription_False_WhenEmpty() {
        // GIVEN
        when(repository.findTopByClientIdAndFundIdOrderByDateDesc("client-001", "3"))
                .thenReturn(Optional.empty());

        // WHEN
        boolean active = adapter.hasActiveSubscription("client-001", "3");

        // THEN
        assertFalse(active, "La suscripción debe considerarse inactiva si no existen transacciones");
    }

}

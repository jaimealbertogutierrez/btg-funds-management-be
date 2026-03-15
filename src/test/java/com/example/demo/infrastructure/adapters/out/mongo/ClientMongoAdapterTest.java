package com.example.demo.infrastructure.adapters.out.mongo;

import com.example.demo.domain.model.Client;
import com.example.demo.infrastructure.adapters.out.mongo.entity.ClientEntity;
import com.example.demo.infrastructure.adapters.out.mongo.repository.SpringDataClientRepository;
import com.example.demo.util.TestDataFactory; // Factoría de datos de prueba
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test de integración para el adaptador de MongoDB de Clientes.
 *
 * Objetivo:
 *  - Validar la correcta interacción entre el adaptador y el repositorio Spring Data.
 *  - Asegurar el mapeo correcto entre entidades Mongo y modelos de dominio.
 *
 * Buenas prácticas:
 *  - Clase package-private (evita "public" innecesario según estándares Sonar).
 *  - Uso de TestDataFactory para crear datos consistentes.
 *  - Verificación de invocaciones a repositorio con Mockito.
 *
 * Autor: Jaime Alberto Gutiérrez
 */
@ExtendWith(MockitoExtension.class)
class ClientMongoAdapterTest {

    // --- Mocks ---
    @Mock
    private SpringDataClientRepository repository; // Repositorio de MongoDB

    // --- Clase bajo prueba ---
    @InjectMocks
    private ClientMongoAdapter adapter; // Adaptador que implementa los métodos de persistencia

    // --- Datos de prueba ---
    private ClientEntity mockEntity; // Entidad simulada
    private Client mockDomain;       // Modelo de dominio simulado

    /**
     * Inicializa datos de prueba antes de cada test.
     * Se usan objetos consistentes de TestDataFactory para mantener uniformidad.
     */
    @BeforeEach
    void setUp() {
        mockEntity = TestDataFactory.createMockClientEntity();
        mockDomain = TestDataFactory.createMockClientDomain();
    }

    // ==========================
    // Tests de findById
    // ==========================

    @Test
    @DisplayName("findById: Debe retornar el modelo de dominio cuando el ID existe")
    void findById_Success() {
        // GIVEN: el repositorio devuelve la entidad simulada
        when(repository.findById("client-123")).thenReturn(Optional.of(mockEntity));
        when(repository.findAll()).thenReturn(Collections.singletonList(mockEntity));

        // WHEN: se llama al adaptador
        Optional<Client> result = adapter.findById("client-123");

        // THEN: se obtiene el dominio correctamente y se verifica llamada al repositorio
        assertTrue(result.isPresent(), "El resultado debe estar presente");
        assertEquals("client-123", result.get().getId(), "El ID del cliente debe coincidir");
        verify(repository).findById("client-123");
    }

    @Test
    @DisplayName("findById: Debe retornar Optional vacío si el repositorio no encuentra nada")
    void findById_NotFound() {
        // GIVEN: repositorio no encuentra el ID
        when(repository.findById("unknown")).thenReturn(Optional.empty());

        // WHEN
        Optional<Client> result = adapter.findById("unknown");

        // THEN
        assertFalse(result.isPresent(), "El resultado debe ser vacío si no se encuentra el cliente");
        verify(repository).findById("unknown");
    }

    // ==========================
    // Tests de save
    // ==========================

    @Test
    @DisplayName("save: Debe mapear a entidad, guardar y retornar el dominio guardado")
    void save_Success() {
        // GIVEN: el repositorio devuelve la entidad simulada al guardar
        when(repository.save(any(ClientEntity.class))).thenReturn(mockEntity);

        // WHEN: se guarda un modelo de dominio
        Client result = adapter.save(mockDomain);

        // THEN: se retorna un dominio no nulo y se verifica llamada al repositorio
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals("client-123", result.getId(), "El ID del cliente guardado debe coincidir");
        verify(repository).save(any(ClientEntity.class));
    }

}

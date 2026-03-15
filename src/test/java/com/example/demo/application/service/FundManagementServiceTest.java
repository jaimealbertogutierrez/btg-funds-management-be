package com.example.demo.application.service;

import com.example.demo.domain.exception.BusinessValidationException;
import com.example.demo.domain.exception.InsufficientBalanceException;
import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;
import com.example.demo.domain.model.Transaction;
import com.example.demo.domain.ports.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * FundManagementServiceTest
 *
 * <p>Clase de pruebas unitarias para {@link FundManagementService} utilizando la metodología
 * Test-Driven Development (TDD) y patrón GIVEN/WHEN/THEN. Cada test está diseñado para cubrir
 * distintos escenarios de negocio, validando tanto los flujos exitosos como las excepciones.
 *
 * <p>Objetivos de la clase:
 * <ul>
 *     <li>Validar la lógica de suscripción a fondos y cancelación.</li>
 *     <li>Verificar que el saldo del cliente se actualiza correctamente.</li>
 *     <li>Confirmar la creación de transacciones en la capa de persistencia.</li>
 *     <li>Verificar la invocación del componente de notificación.</li>
 *     <li>Cubrir las ramas de error: saldo insuficiente, suscripción ya activa, cliente no encontrado.</li>
 * </ul>
 *
 * <p>Nota: Se utiliza Mockito para simular los puertos de salida y así mantener las pruebas
 * aisladas del almacenamiento real (MongoDB). Esto permite alta cobertura sin depender de la base de datos.
 *
 * @author Jaime Alberto Gutiérrez
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class FundManagementServiceTest {

    /** Repositorio simulado para clientes. */
    @Mock
    private ClientRepositoryPort clientRepository;

    /** Repositorio simulado para fondos. */
    @Mock
    private FundRepositoryPort fundRepository;

    /** Repositorio simulado para transacciones. */
    @Mock
    private TransactionRepositoryPort transactionRepository;

    /** Adaptador simulado para notificaciones. */
    @Mock
    private NotificationPort notificationPort;

    /** Servicio bajo prueba con dependencias inyectadas simuladas. */
    @InjectMocks
    private FundManagementService fundService;

    /** Cliente de prueba usado en múltiples escenarios. */
    private Client mockClient;

    /** Fondo de prueba usado en múltiples escenarios. */
    private Fund mockFund;

    /**
     * Configuración inicial para cada test.
     * Se crean instancias de cliente y fondo con valores por defecto.
     */
    @BeforeEach
    void setUp() {
        mockClient = new Client();
        mockClient.setId("client-001");
        mockClient.setName("Candidato BTG");
        mockClient.setBalance(500000.0);

        mockFund = new Fund();
        mockFund.setId("3");
        mockFund.setName("DEUDAPRIVADA");
        mockFund.setMinAmount(50000.0);
        mockFund.setCategory("FIC");
    }

    /**
     * Test de suscripción exitosa.
     *
     * <p><b>GIVEN:</b> Cliente con saldo suficiente y fondo disponible, sin suscripción activa.
     * <p><b>WHEN:</b> Se llama a subscribeToFund.
     * <p><b>THEN:</b> Se descuenta saldo, se crea transacción de tipo "SUBSCRIPTION",
     * se guarda el cliente y se notifica.
     */
    @Test
    @DisplayName("Suscripción Exitosa: Debe descontar saldo y guardar transacción")
    void subscribeToFund_Success() {
        when(clientRepository.findById("client-001")).thenReturn(Optional.of(mockClient));
        when(fundRepository.findById("3")).thenReturn(Optional.of(mockFund));
        when(transactionRepository.hasActiveSubscription("client-001", "3")).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        Transaction result = fundService.subscribeToFund("client-001", "3");

        assertEquals(450000.0, mockClient.getBalance());
        assertEquals("SUBSCRIPTION", result.getType());
        verify(notificationPort).notifySubscription(any(), any());
        verify(clientRepository).save(mockClient);
    }

    /**
     * Test de suscripción fallida por saldo insuficiente.
     *
     * <p><b>GIVEN:</b> Cliente con saldo menor al requerido para el fondo.
     * <p><b>WHEN:</b> Se llama a subscribeToFund.
     * <p><b>THEN:</b> Se lanza {@link InsufficientBalanceException} y no se guarda transacción.
     */
    @Test
    @DisplayName("Suscripción Fallida: Saldo insuficiente")
    void subscribeToFund_InsufficientBalance() {
        mockClient.setBalance(30000.0);
        when(clientRepository.findById("client-001")).thenReturn(Optional.of(mockClient));
        when(fundRepository.findById("3")).thenReturn(Optional.of(mockFund));

        assertThrows(InsufficientBalanceException.class,
                () -> fundService.subscribeToFund("client-001", "3"));

        verify(transactionRepository, never()).save(any());
    }

    /**
     * Test de suscripción fallida por suscripción ya activa.
     *
     * <p><b>GIVEN:</b> Cliente con saldo suficiente y suscripción activa en el fondo.
     * <p><b>WHEN:</b> Se llama a subscribeToFund.
     * <p><b>THEN:</b> Se lanza {@link BusinessValidationException}.
     */
    @Test
    @DisplayName("Suscripción Fallida: Ya existe una suscripción activa")
    void subscribeToFund_AlreadyActive() {
        when(clientRepository.findById("client-001")).thenReturn(Optional.of(mockClient));
        when(fundRepository.findById("3")).thenReturn(Optional.of(mockFund));
        when(transactionRepository.hasActiveSubscription("client-001", "3")).thenReturn(true);

        BusinessValidationException ex = assertThrows(BusinessValidationException.class,
                () -> fundService.subscribeToFund("client-001", "3"));

        assertTrue(ex.getMessage().contains("ya posee una suscripción activa"));
    }

    /**
     * Test de cancelación exitosa de suscripción.
     *
     * <p><b>GIVEN:</b> Cliente con saldo parcial y suscripción activa.
     * <p><b>WHEN:</b> Se llama a cancelSubscription.
     * <p><b>THEN:</b> Se devuelve saldo, se crea transacción de tipo "CANCELLATION" y se guarda el cliente.
     */
    @Test
    @DisplayName("Cancelación Exitosa: Debe devolver saldo al cliente")
    void cancelSubscription_Success() {
        mockClient.setBalance(450000.0);
        when(clientRepository.findById("client-001")).thenReturn(Optional.of(mockClient));
        when(fundRepository.findById("3")).thenReturn(Optional.of(mockFund));
        when(transactionRepository.hasActiveSubscription("client-001", "3")).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        Transaction result = fundService.cancelSubscription("client-001", "3");

        assertEquals(500000.0, mockClient.getBalance());
        assertEquals("CANCELLATION", result.getType());
        verify(clientRepository).save(mockClient);
    }

    /**
     * Test de suscripción fallida por cliente inexistente.
     *
     * <p><b>GIVEN:</b> Cliente no registrado.
     * <p><b>WHEN:</b> Se llama a subscribeToFund con ID desconocido.
     * <p><b>THEN:</b> Se lanza {@link ResourceNotFoundException}.
     */
    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException si el cliente no existe")
    void clientNotFound() {
        when(clientRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fundService.subscribeToFund("unknown", "3"));
    }

} // Fin del Test unitario

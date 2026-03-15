package com.example.demo.infrastructure.adapters.out.mongo.notification;

import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MockNotificationAdapterTest {

    private MockNotificationAdapter notificationAdapter;
    private Client mockClient;
    private Fund mockFund;

    @BeforeEach
    void setUp() {
        notificationAdapter = new MockNotificationAdapter();

        // Inicialización de objetos de dominio (usando setters según tu modelo)
        mockClient = new Client();
        mockClient.setId("client-001");

        mockFund = new Fund();
        mockFund.setName("DEUDAPRIVADA");
        mockFund.setMinAmount(50000.0);
    }

    @Test
    @DisplayName("Debe procesar correctamente la notificación por EMAIL")
    void notifySubscription_Email() {
        // Arrange
        mockClient.setNotificationPreference("EMAIL");

        // Act & Assert
        assertDoesNotThrow(() ->
                        notificationAdapter.notifySubscription(mockClient, mockFund),
                "La notificación por EMAIL no debe lanzar excepciones"
        );
    }

    @Test
    @DisplayName("Debe procesar correctamente la notificación por SMS")
    void notifySubscription_Sms() {
        // Arrange
        mockClient.setNotificationPreference("sms"); // Probamos case-insensitive

        // Act & Assert
        assertDoesNotThrow(() ->
                        notificationAdapter.notifySubscription(mockClient, mockFund),
                "La notificación por SMS no debe lanzar excepciones"
        );
    }

    @Test
    @DisplayName("Debe procesar la notificación por defecto cuando la preferencia no es reconocida")
    void notifySubscription_Default() {
        // Arrange
        mockClient.setNotificationPreference("PUSH_NOTIFICATION");

        // Act & Assert
        assertDoesNotThrow(() ->
                        notificationAdapter.notifySubscription(mockClient, mockFund),
                "La notificación por defecto no debe lanzar excepciones"
        );
    }

    @Test
    @DisplayName("Debe procesar la notificación incluso si la preferencia es nula")
    void notifySubscription_NullPreference() {
        // Arrange
        mockClient.setNotificationPreference(null);

        // Act & Assert
        assertDoesNotThrow(() ->
                        notificationAdapter.notifySubscription(mockClient, mockFund),
                "La notificación con preferencia nula debe caer en el bloque else sin fallar"
        );
    }

}
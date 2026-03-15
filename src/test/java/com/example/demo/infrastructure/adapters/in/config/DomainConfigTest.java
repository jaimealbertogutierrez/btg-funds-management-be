package com.example.demo.infrastructure.adapters.in.config;

import com.example.demo.application.service.FundManagementService;
import com.example.demo.domain.ports.in.FundManagementUseCase;
import com.example.demo.domain.ports.out.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DomainConfigTest
 *
 * <p>Pruebas unitarias de la clase {@link DomainConfig} para verificar la creación de Beans
 * de dominio, específicamente el Bean {@link FundManagementUseCase}.
 *
 * <p>Se utiliza Mockito para simular los puertos de salida requeridos por el servicio,
 * validando que la inyección de dependencias sea correcta y que el Bean generado sea del tipo
 * esperado.
 *
 * <p>Autor: Jaime Alberto Gutiérrez
 * <br>Versión: 1.0
 */
@ExtendWith(MockitoExtension.class)
class DomainConfigTest {

    // Mocks de los puertos necesarios para la construcción del servicio
    @Mock
    private ClientRepositoryPort clientRepositoryPort;
    @Mock
    private FundRepositoryPort fundRepositoryPort;
    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;
    @Mock
    private NotificationPort notificationPort;

    /**
     * Test que valida la creación del Bean {@link FundManagementUseCase}.
     *
     * <p>GIVEN: Se tienen mocks de todos los puertos necesarios para el servicio de gestión de fondos.
     * <p>WHEN: Se invoca el método {@link DomainConfig#fundManagementUseCase(ClientRepositoryPort,
     * FundRepositoryPort, TransactionRepositoryPort, NotificationPort)} para crear el Bean.
     * <p>THEN: El Bean devuelto no debe ser nulo y debe ser una instancia de {@link FundManagementService}.
     */
    @Test
    @DisplayName("Debe crear el Bean de FundManagementUseCase correctamente")
    void fundManagementUseCaseBeanCreation() {
        // Arrange
        DomainConfig domainConfig = new DomainConfig();

        // Act
        FundManagementUseCase useCase = domainConfig.fundManagementUseCase(
                clientRepositoryPort,
                fundRepositoryPort,
                transactionRepositoryPort,
                notificationPort
        );

        // Assert
        assertNotNull(useCase, "El Bean de Caso de Uso no debe ser nulo");
        assertTrue(useCase instanceof FundManagementService,
                "El Bean debe ser una instancia de FundManagementService");
    }

}

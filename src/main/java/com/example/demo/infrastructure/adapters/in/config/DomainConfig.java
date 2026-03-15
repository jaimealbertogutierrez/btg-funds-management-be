package com.example.demo.infrastructure.adapters.in.config;

import com.example.demo.application.service.FundManagementService;
import com.example.demo.domain.ports.in.FundManagementUseCase;
import com.example.demo.domain.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>Configuración de dominio: DomainConfig</h2>
 *
 * <p>
 * Clase de configuración Spring que define cómo se construyen los beans del dominio.
 * Forma parte de la infraestructura de entrada (adapters.in) y permite inyectar
 * las dependencias necesarias para la capa de aplicación, manteniendo el núcleo
 * de dominio desacoplado de Spring y de la infraestructura específica.
 * </p>
 *
 * <h3>Responsabilidad</h3>
 * <ul>
 *     <li>Exponer el caso de uso principal FundManagementUseCase como un bean gestionado por Spring.</li>
 *     <li>Inyectar de manera segura los puertos de salida (repositories y notification) en la implementación del servicio.</li>
 *     <li>Garantizar que la lógica de negocio (FundManagementService) pueda ser utilizada
 *         por controladores o adaptadores sin acoplar el dominio a Spring directamente.</li>
 * </ul>
 */
@Configuration
public class DomainConfig {

    /**
     * Crea y configura el bean FundManagementUseCase.
     *
     * <p>
     * Este método construye la implementación FundManagementService e inyecta los
     * puertos de salida necesarios:
     * </p>
     * <ul>
     *     <li>ClientRepositoryPort</li>
     *     <li>FundRepositoryPort</li>
     *     <li>TransactionRepositoryPort</li>
     *     <li>NotificationPort</li>
     * </ul>
     *
     * <p>
     * La inyección asegura que el dominio permanezca desacoplado y que la lógica
     * de negocio pueda ser fácilmente testeada o sustituida por mocks en pruebas unitarias.
     * </p>
     *
     * @param clientRepositoryPort Puerto de salida para clientes
     * @param fundRepositoryPort Puerto de salida para fondos
     * @param transactionRepositoryPort Puerto de salida para transacciones
     * @param notificationPort Puerto de salida para notificaciones
     * @return FundManagementUseCase Bean del caso de uso de gestión de fondos
     */
    @Bean
    public FundManagementUseCase fundManagementUseCase(
            ClientRepositoryPort clientRepositoryPort,
            FundRepositoryPort fundRepositoryPort,
            TransactionRepositoryPort transactionRepositoryPort,
            NotificationPort notificationPort) {

        return new FundManagementService(
                clientRepositoryPort,
                fundRepositoryPort,
                transactionRepositoryPort,
                notificationPort
        );
    }
}

package com.example.demo.application.service;

import com.example.demo.domain.exception.BusinessValidationException;
import com.example.demo.domain.exception.InsufficientBalanceException;
import com.example.demo.domain.exception.ResourceNotFoundException;
import com.example.demo.domain.model.Client;
import com.example.demo.domain.model.Fund;
import com.example.demo.domain.model.Transaction;

import com.example.demo.domain.ports.out.*;
import com.example.demo.domain.ports.in.FundManagementUseCase;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementación del caso de uso {@link FundManagementUseCase}.
 *
 * <p>
 * Esta clase pertenece a la capa de aplicación dentro de una arquitectura
 * hexagonal (Ports & Adapters). Su responsabilidad principal es orquestar
 * la lógica de negocio relacionada con la gestión de suscripciones a fondos,
 * delegando el acceso a infraestructura mediante puertos definidos en el dominio.
 * </p>
 *
 * <p>
 * En el contexto de la arquitectura:
 *
 * <ul>
 * <li><b>Dominio:</b> Contiene las entidades y reglas de negocio.</li>
 * <li><b>Application:</b> Coordina los casos de uso del sistema.</li>
 * <li><b>Ports:</b> Definen contratos de comunicación con sistemas externos.</li>
 * <li><b>Adapters:</b> Implementan dichos contratos hacia tecnologías concretas
 * como bases de datos, servicios externos o sistemas de mensajería.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Este servicio no depende de detalles de infraestructura específicos.
 * Todas las interacciones externas se realizan a través de interfaces
 * (puertos), lo que garantiza:
 * </p>
 *
 * <ul>
 * <li>Alta desacoplación</li>
 * <li>Facilidad de pruebas unitarias</li>
 * <li>Intercambiabilidad de implementaciones</li>
 * <li>Mayor mantenibilidad</li>
 * </ul>
 *
 * <p>
 * Responsabilidades principales del servicio:
 * </p>
 *
 * <ul>
 * <li>Gestionar la suscripción de clientes a fondos de inversión.</li>
 * <li>Validar reglas de negocio antes de ejecutar operaciones.</li>
 * <li>Registrar transacciones asociadas a operaciones financieras.</li>
 * <li>Coordinar notificaciones a otros sistemas o servicios.</li>
 * </ul>
 *
 * <p>
 * Todas las operaciones son ejecutadas respetando las reglas de negocio
 * definidas en el dominio, y las validaciones necesarias para garantizar
 * la integridad de los datos y del flujo de negocio.
 * </p>
 *
 * @author Jaime Alberto Gutierrez
 * Analista Programador Java Backend
 */
public class FundManagementService implements FundManagementUseCase {

    /**
     * Puerto de salida encargado de la persistencia y recuperación de clientes.
     *
     * <p>
     * Este puerto abstrae el mecanismo de almacenamiento de clientes,
     * permitiendo que el dominio no dependa de tecnologías concretas
     * como bases de datos relacionales o NoSQL.
     * </p>
     */
    private final ClientRepositoryPort clientRepository;

    /**
     * Puerto de salida responsable de la gestión de fondos disponibles
     * en el sistema.
     *
     * <p>
     * Permite recuperar información de fondos y validar su existencia
     * dentro del catálogo de productos financieros.
     * </p>
     */
    private final FundRepositoryPort fundRepository;

    /**
     * Puerto de salida encargado del registro y consulta de transacciones.
     *
     * <p>
     * Las transacciones representan eventos financieros relevantes
     * dentro del sistema, como suscripciones o cancelaciones de fondos.
     * </p>
     */
    private final TransactionRepositoryPort transactionRepository;

    /**
     * Puerto de salida encargado de enviar notificaciones hacia
     * sistemas externos o mecanismos de comunicación.
     *
     * <p>
     * La implementación concreta podría incluir:
     * </p>
     *
     * <ul>
     * <li>Servicios de correo electrónico</li>
     * <li>Sistemas de mensajería</li>
     * <li>Notificaciones push</li>
     * <li>Integraciones con sistemas externos</li>
     * </ul>
     */
    private final NotificationPort notificationPort;

    /**
     * Constructor del servicio que recibe las dependencias necesarias
     * para la ejecución de los casos de uso.
     *
     * <p>
     * Todas las dependencias son definidas como puertos, lo que permite
     * desacoplar la lógica de negocio de cualquier detalle de infraestructura.
     * </p>
     *
     * @param clientRepository puerto de acceso a la persistencia de clientes
     * @param fundRepository puerto de acceso a la persistencia de fondos
     * @param transactionRepository puerto de acceso a la persistencia de transacciones
     * @param notificationPort puerto de envío de notificaciones
     */
    public FundManagementService(ClientRepositoryPort clientRepository,
                                 FundRepositoryPort fundRepository,
                                 TransactionRepositoryPort transactionRepository,
                                 NotificationPort notificationPort) {
        this.clientRepository = clientRepository;
        this.fundRepository = fundRepository;
        this.transactionRepository = transactionRepository;
        this.notificationPort = notificationPort;
    }

    /**
     * Permite a un cliente suscribirse a un fondo de inversión.
     *
     * <p>
     * Este método implementa el flujo completo de suscripción a un fondo,
     * incluyendo validaciones de negocio, actualización de saldo y registro
     * de la transacción correspondiente.
     * </p>
     *
     * <p>
     * Flujo de ejecución:
     * </p>
     *
     * <ol>
     * <li>Validar que el cliente exista.</li>
     * <li>Validar que el fondo exista.</li>
     * <li>Verificar que el cliente no tenga ya una suscripción activa.</li>
     * <li>Verificar que el cliente tenga saldo suficiente.</li>
     * <li>Descontar el valor mínimo del fondo del saldo del cliente.</li>
     * <li>Registrar la transacción de suscripción.</li>
     * <li>Enviar una notificación asociada a la operación.</li>
     * </ol>
     *
     * <p>
     * Reglas de negocio aplicadas:
     * </p>
     *
     * <ul>
     * <li>Un cliente no puede suscribirse dos veces al mismo fondo si
     * ya posee una suscripción activa.</li>
     * <li>El cliente debe tener saldo suficiente para cubrir el monto
     * mínimo requerido por el fondo.</li>
     * </ul>
     *
     * @param clientId identificador único del cliente
     * @param fundId identificador único del fondo
     *
     * @return transacción registrada que representa la suscripción
     *
     * @throws ResourceNotFoundException si el cliente o el fondo no existen
     * @throws BusinessValidationException si el cliente ya tiene una suscripción activa
     * @throws InsufficientBalanceException si el cliente no posee saldo suficiente
     */
    @Override
    public Transaction subscribeToFund(String clientId, String fundId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clientId));

        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new ResourceNotFoundException("Fondo no encontrado con id: " + fundId));

        // Validación: Un cliente no puede suscribirse dos veces al mismo fondo si ya está activo
        if (transactionRepository.hasActiveSubscription(clientId, fundId)) {
            throw new BusinessValidationException("El cliente ya posee una suscripción activa a este fondo.");
        }

        // Validación principal: Saldo disponible (Regla de negocio de la prueba)
        if (client.getBalance().compareTo(fund.getMinAmount()) < 0) {
            throw new InsufficientBalanceException(fund.getName());
        }

        // 1. Descontar el saldo del cliente
        client.deductBalance(fund.getMinAmount());
        clientRepository.save(client);

        // 2. Registrar la transacción de suscripción
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(), // Identificador único generado
                clientId,
                fundId,
                "SUBSCRIPTION",
                fund.getMinAmount(),
                LocalDateTime.now()
        );
        Transaction savedTransaction = transactionRepository.save(transaction);

        // 3. Enviar notificación asíncrona o sincrona a través del puerto
        notificationPort.notifySubscription(client, fund);

        return savedTransaction;
    }

    /**
     * Permite cancelar una suscripción activa de un cliente a un fondo.
     *
     * <p>
     * Este método revierte el proceso de suscripción, restaurando el saldo
     * del cliente y registrando la transacción correspondiente.
     * </p>
     *
     * <p>
     * Flujo de ejecución:
     * </p>
     *
     * <ol>
     * <li>Validar que el cliente exista.</li>
     * <li>Validar que el fondo exista.</li>
     * <li>Verificar que exista una suscripción activa.</li>
     * <li>Devolver el valor del fondo al saldo del cliente.</li>
     * <li>Registrar la transacción de cancelación.</li>
     * </ol>
     *
     * <p>
     * Regla de negocio:
     * </p>
     *
     * <ul>
     * <li>No es posible cancelar una suscripción que no existe
     * o que ya fue cancelada previamente.</li>
     * </ul>
     *
     * @param clientId identificador del cliente
     * @param fundId identificador del fondo
     *
     * @return transacción registrada que representa la cancelación
     *
     * @throws ResourceNotFoundException si el cliente o el fondo no existen
     * @throws BusinessValidationException si no existe una suscripción activa
     */
    @Override
    public Transaction cancelSubscription(String clientId, String fundId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clientId));

        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new ResourceNotFoundException("Fondo no encontrado con id: " + fundId));

        // Validación: Verificar que realmente exista una suscripción activa para cancelar
        if (!transactionRepository.hasActiveSubscription(clientId, fundId)) {
            throw new BusinessValidationException("El cliente no tiene una suscripción activa en este fondo para cancelar.");
        }

        // 1. Retornar el valor de vinculación al cliente
        client.addBalance(fund.getMinAmount());
        clientRepository.save(client);

        // 2. Registrar la transacción de cancelación
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                clientId,
                fundId,
                "CANCELLATION",
                fund.getMinAmount(),
                LocalDateTime.now()
        );

        return transactionRepository.save(transaction);
    }

} // Fin de la clase

package com.example.demo.domain.ports.in;

import com.example.demo.domain.model.Transaction;

/**
 * <h2>Puerto de entrada (Inbound Port): FundManagementUseCase</h2>
 *
 * <p>
 * Define la interfaz de casos de uso relacionados con la gestión de fondos
 * en el sistema. Forma parte del núcleo de dominio de la arquitectura hexagonal.
 * Esta interfaz abstrae la lógica de negocio para suscripciones y cancelaciones
 * de fondos, permitiendo que la capa de aplicación (services) implemente
 * los detalles sin depender de infraestructura o tecnología específica.
 * </p>
 *
 * <h3>Responsabilidad</h3>
 *
 * <ul>
 *     <li>Exponer operaciones de suscripción a un fondo para un cliente.</li>
 *     <li>Exponer operaciones de cancelación de suscripción de un fondo.</li>
 *     <li>Garantizar que las reglas de negocio se apliquen en la capa de dominio,
 *         manteniendo el desacoplamiento de la infraestructura.</li>
 * </ul>
 */
public interface FundManagementUseCase {

    /**
     * Suscribe un cliente a un fondo específico.
     *
     * <p>
     * Esta operación realiza las siguientes acciones (implementadas en la capa de servicio):
     * </p>
     * <ul>
     *     <li>Valida que el cliente y el fondo existan.</li>
     *     <li>Verifica que no exista una suscripción activa previa.</li>
     *     <li>Comprueba que el saldo del cliente sea suficiente para el monto mínimo del fondo.</li>
     *     <li>Descuenta el saldo del cliente.</li>
     *     <li>Registra la transacción de tipo "SUBSCRIPTION".</li>
     *     <li>Envía notificación al cliente.</li>
     * </ul>
     *
     * @param clientId Identificador único del cliente
     * @param fundId Identificador único del fondo
     * @return Transaction transacción registrada con éxito
     * @throws com.example.demo.domain.exception.ResourceNotFoundException si el cliente o el fondo no existen
     * @throws com.example.demo.domain.exception.BusinessValidationException si ya existe una suscripción activa
     * @throws com.example.demo.domain.exception.InsufficientBalanceException si el saldo del cliente es insuficiente
     */
    Transaction subscribeToFund(String clientId, String fundId);

    /**
     * Cancela la suscripción de un cliente a un fondo específico.
     *
     * <p>
     * Esta operación realiza las siguientes acciones (implementadas en la capa de servicio):
     * </p>
     * <ul>
     *     <li>Valida que el cliente y el fondo existan.</li>
     *     <li>Verifica que exista una suscripción activa para cancelar.</li>
     *     <li>Retorna el monto del fondo al saldo del cliente.</li>
     *     <li>Registra la transacción de tipo "CANCELLATION".</li>
     * </ul>
     *
     * @param clientId Identificador único del cliente
     * @param fundId Identificador único del fondo
     * @return Transaction transacción de cancelación registrada
     * @throws com.example.demo.domain.exception.ResourceNotFoundException si el cliente o el fondo no existen
     * @throws com.example.demo.domain.exception.BusinessValidationException si no existe una suscripción activa
     */
    Transaction cancelSubscription(String clientId, String fundId);
}

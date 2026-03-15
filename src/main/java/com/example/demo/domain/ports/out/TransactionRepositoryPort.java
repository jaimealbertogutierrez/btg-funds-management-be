package com.example.demo.domain.ports.out;

import com.example.demo.domain.model.Transaction;
import java.util.List;

/**
 * <h2>Puerto de salida (Outbound Port): TransactionRepositoryPort</h2>
 *
 * <p>
 * Define la interfaz de acceso y manipulación de transacciones dentro del dominio.
 * Forma parte del núcleo de dominio en arquitectura hexagonal, permitiendo que la lógica
 * de negocio interactúe con la persistencia de transacciones sin depender de la tecnología
 * subyacente (MongoDB, SQL, etc.).
 * </p>
 *
 * <h3>Responsabilidad</h3>
 * <ul>
 *     <li>Guardar y registrar transacciones de suscripción y cancelación.</li>
 *     <li>Obtener historial de transacciones de un cliente para auditoría o validación.</li>
 *     <li>Verificar la existencia de suscripciones activas de un cliente a un fondo.</li>
 *     <li>Desacoplar la capa de dominio de la implementación concreta de persistencia.</li>
 * </ul>
 */
public interface TransactionRepositoryPort {

    /**
     * Persiste una transacción en el repositorio.
     *
     * <p>
     * Se utiliza para registrar suscripciones y cancelaciones de fondos,
     * garantizando la trazabilidad de los movimientos financieros del cliente.
     * </p>
     *
     * @param transaction Transacción a guardar
     * @return Transaction transacción persistida con éxito
     */
    Transaction save(Transaction transaction);

    /**
     * Obtiene todas las transacciones asociadas a un cliente.
     *
     * <p>
     * Permite consultar el historial financiero de un cliente, generar reportes,
     * auditorías y validar reglas de negocio basadas en transacciones previas.
     * </p>
     *
     * @param clientId Identificador del cliente
     * @return List<Transaction> Lista de transacciones asociadas al cliente
     */
    List<Transaction> findByClientId(String clientId);

    /**
     * Verifica si un cliente tiene una suscripción activa a un fondo específico.
     *
     * <p>
     * Este método es crítico para la lógica de negocio: evita suscripciones
     * duplicadas y permite controlar la validez de cancelaciones.
     * </p>
     *
     * @param clientId Identificador del cliente
     * @param fundId Identificador del fondo
     * @return boolean true si existe una suscripción activa, false si no
     */
    boolean hasActiveSubscription(String clientId, String fundId);
}
